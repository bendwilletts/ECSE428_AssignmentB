package ca.mcgill.ecse428.main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String emailAddress = "ecse428user@gmail.com";
		String emailPassword = "mcgillecse428";
		String PATH_TO_CHROME_DRIVER = "/Users/bendwilletts/Downloads/chromedriver"; //Change path to your local chromedriver.exe
		
		EmailWithImage email = new EmailWithImage(emailAddress, emailPassword, PATH_TO_CHROME_DRIVER);
		
		boolean val = email.signIn();
		System.out.println(val);
	}

}
