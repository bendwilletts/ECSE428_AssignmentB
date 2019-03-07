package ca.mcgill.ecse428.main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String emailAddress = "ecse428user@gmail.com";
		String emailPassword = "mcgillecse428";
		String PATH_TO_CHROME_DRIVER = "/Users/bendwilletts/Downloads/chromedriver"; //Change path to your local chromedriver.exe
		String PATH_TO_IMAGE_FILES = "/Users/bendwilletts/eclipse-workspace/ecse428-assignment-B/resources/img/";
		
		EmailWithImage email = new EmailWithImage(emailAddress, emailPassword, PATH_TO_CHROME_DRIVER);
		
		boolean val = email.signIn();
		System.out.println("Signed In = " + val);
		
		email.sendEmailWithImage("benjamin.willetts@gmail.com", PATH_TO_IMAGE_FILES+"testfile5.png");
		System.out.println("Email Sent? = " + email.confirmSentEmailCount());
		
		email.resetInitialState();
		
		email.sendEmailWithImage("benjamin.willetts@gmail.com", PATH_TO_IMAGE_FILES+"testfile1.gif", "TEST SUBJECT", "TEST MESSAGE");
		System.out.println("Email Sent? = " + email.confirmSentEmailCount());
		
		email.resetInitialState();

		
	}

}
