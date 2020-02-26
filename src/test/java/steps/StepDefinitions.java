package steps;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import flow.IAction;
import flow.IEvent;
import flow.samples.TextAction;
import flow.samples.TextAdapter;
import flow.samples.TextAgent;
import flow.samples.TextEvent;

public class StepDefinitions {

	List<IAction> actionList;
	List<IEvent> eventList = new ArrayList<IEvent>();

	@Given("^A User sends an \"([^\"]*)\" message$")
	public void a_User_sends_an_message(String message) throws Throwable {

		TextAgent agent = new TextAgent(message);
		actionList = agent.act();

	}

	@When("^The message is converted by the Adapter$")
	public void the_message_is_converted_by_the_Adapter() throws Throwable {
		TextAdapter adapter = new TextAdapter();

		for (IAction action : actionList) {
			eventList.add(adapter.adapt(action));
		}

	}

	@Then("^The Message server should contain the \"([^\"]*)\" message in the queue$")
	public void the_Message_server_should_contain_the_message_in_the_queue(String message) throws Throwable {

		for (IEvent event : eventList) {

			Assert.assertEquals(((String) event.trigger()).contains(message), true);
		}

	}

}
