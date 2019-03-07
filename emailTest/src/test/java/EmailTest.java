

import cucumber.annotation.en.Given;
 

public class EmailTest {
	
	//Test Account Info
	private final String EMAIL_ADDRESS = "ecse428user@gmail.com";
	private final String EMAIL_PASSWORD = "mcgillecse428";
	
//	//Change path to your local chromedriver.exe
//	private final String PATH_TO_CHROME_DRIVER = "/Users/bendwilletts/Downloads/chromedriver";
//	private final String PATH_TO_IMAGE_FILES = "/Users/bendwilletts/eclipse-workspace/ecse428-assignment-B/emailTest/resources/img/";
	
	String PATH_TO_CHROME_DRIVER = "/Users/udaysahni/Downloads/chromedriver";
	String PATH_TO_IMAGE_FILES = "/Users/udaysahni/Documents/School/ECSE428/emailTest/resources/img/";

	private EmailWithImage email;
	
	@Given("^I am logged into a Gmail account$")
	public void loginToGmailAccount() {
		email = new EmailWithImage(EMAIL_ADDRESS, EMAIL_PASSWORD, PATH_TO_CHROME_DRIVER);
		email.signIn();
	}
}

