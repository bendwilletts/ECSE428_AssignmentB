

import cucumber.annotation.en.Given;
import cucumber.annotation.en.When;
import cucumber.annotation.en.And;
import cucumber.annotation.en.Then;
import cucumber.annotation.Before;
import cucumber.annotation.After;

public class EmailTest {
	
	//Test Account Info
	private final String EMAIL_ADDRESS = "ecse428user@gmail.com";
	private final String EMAIL_PASSWORD = "mcgillecse428";
	
	//Change path to your local chromedriver.exe
//	private final String PATH_TO_CHROME_DRIVER = "/Users/bendwilletts/Downloads/chromedriver";
//	private final String PATH_TO_IMAGE_FILES = "/Users/bendwilletts/eclipse-workspace/ecse428-assignment-B/emailTest/resources/img/";
	
	String PATH_TO_CHROME_DRIVER = "/Users/udaysahni/Downloads/chromedriver";
	String PATH_TO_IMAGE_FILES = "/Users/udaysahni/Documents/School/ECSE428/emailTest/resources/img/";

	private EmailWithImage email;
	
	@Before
	public void setup() {
		email = new EmailWithImage(EMAIL_ADDRESS, EMAIL_PASSWORD, PATH_TO_CHROME_DRIVER);
		email.signIn();
	}
	
	@After
	public void cleanup() {
		email.shutdownDriver();
	}
	
	@Given("^I am logged into a Gmail account$")
	public void loginToGmailAccount() throws Exception {
		if(!email.checkInitialState()) {
			throw new Exception("Not logged in!");
		};
	}
	
	@When("^I compose an email$")
	public void composeEmail() {
		email.clickComposeButton();
	}
	
	@And("^I specify \"([^\"]*)\" as the recipient$")
	public void specifyRecipient(String recipient) {
		email.enterRecipientEmail(recipient);
	}
	
	@And("^I CC \"([^\"]*)\" on the email$")
	public void specifyCCRecipient(String ccrecipient) {
		email.clickCCButton();
		email.enterCCEmail(ccrecipient);
	}
	
	@And("^I specify an invalid recipient \"([^\"]*)\"$")
	public void specifyInvalidRecipient(String invalidRecipient) {
		email.enterRecipientEmail(invalidRecipient);
	}
	
	@And("^I attach a local file \"([^\"]*)\" to the email$")
	public void attachLocalFile(String file) {
		email.attachFile(PATH_TO_IMAGE_FILES + file);
	}
	
	@And("^I send the email$")
	public void sendEmail() {
		email.clickSendButton();
	}
	
	@Then("^the recipient should receive the email with the attached file$")
	public void recipientRecievesEmailWithFile() throws Exception {
		if(!email.confirmSentEmailCount()) {
			throw new Exception("Email not sent!");
		}
		email.resetInitialState();
	}
	
	@Then("^I should receive an error message notifying me that the email was not recognised$")
	public void errorMessageRecieved() throws Exception {
		if(!email.confirmNotRecognisedError()) {
			throw new Exception("Error not recieved");
		}
		email.resetInitialState();
	}
}

