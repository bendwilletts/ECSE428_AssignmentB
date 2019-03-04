package ca.mcgill.ecse428.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class EmailWithImage {

	private WebDriver driver;
	private String goToUrl;
	private String fileName;
	private String recipient;
	
	private final String PATH_TO_CHROME_DRIVER = "/Users/bendwilletts/Downloads/chromedriver";
	private final String SIGNIN_URL = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
	private final String EMAIL_ADDRESS = "ecse428user@gmail.com";
	private final String EMAIL_PASS = "mcgillecse428";
	
}
