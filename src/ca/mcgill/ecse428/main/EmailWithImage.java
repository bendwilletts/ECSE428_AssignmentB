package ca.mcgill.ecse428.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailWithImage {
	
	//Initialize class variables
	private WebDriver driver;
	private String chromeDriverPath;
	private String currentUrl;
	private String storedFileName;
	private String storedRecipientEmail;
	private String emailAddress;
	private String emailPassword;
	private boolean signedIn;
	private int sentEmailCount;
	
	private final String SIGN_IN_URL = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
	private final String EMAIL_TEXT_INPUT = "input[type='email']";
	private final String PASSWORD_TEXT_INPUT = "input[type='password']";
	private final String EMAIL_NEXT_BUTTON = "identifierNext";
	private final String PASSWORD_NEXT_BUTTON = "passwordNext";
	private final String INBOX_URL = "https://mail.google.com/mail/#inbox";
	private final String SENT_URL = "https://mail.google.com/mail/#sent";
	private final String MSG_COUNT_SPAN = "span.ts";
	
	//Class Constructor
	public EmailWithImage(String emailAddress, String emailPassword, String chromeDriverPath) {
		this.chromeDriverPath = chromeDriverPath;
		setupWebDriver();
		this.emailAddress = emailAddress;
		this.emailPassword = emailPassword;
		this.signedIn = false;
		
	}
	
	public boolean signIn() {
		//Visit sign in page and sign in to account
		if (emailAddress == null || emailPassword == null) {
			System.out.println("Email address or password missing!");
			return false;
		}
		try {
			visitUrl(SIGN_IN_URL);
			
			//Enter email address
			WebElement emailInput = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(EMAIL_TEXT_INPUT)));
			emailInput.sendKeys(emailAddress);
			WebElement emailNextButton = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.id(EMAIL_NEXT_BUTTON)));
			emailNextButton.click();
			
			//Enter email password
			WebElement passwordInput = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(PASSWORD_TEXT_INPUT)));
			passwordInput.sendKeys(emailPassword);
			WebElement passwordNextButton = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.id(PASSWORD_NEXT_BUTTON)));
			passwordNextButton.click();
			
			//Check if user is signedIn
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.titleContains("Inbox"));
			String checkUrl = driver.getCurrentUrl();
			if (!checkUrl.equals(INBOX_URL)) {
				System.out.println("Email or Password Incorrect!");
				return false;
			}
			
			signedIn = true;
			return true;
		} catch (Exception e) {
			System.out.println("Sign In Failed!");
			return false;
		}
	}
	
	public boolean sendEmailWithImage() {
		//Send email with an image attachment to a recipient
		return sendEmailWithImage(storedRecipientEmail, storedFileName);
	}
	
	public boolean sendEmailWithImage(String recipientEmail, String fileName) {
		//Send email with an image attachment to a recipient
		return true;
	}
	
	public void updateSentEmailCount() {
		//visit SENT_URL and obtains sent email count
		try {
			visitUrl(SENT_URL);
			sentEmailCount = findMessageCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean confirmSentEmailCount() {
		//visit SENT_URL and checks if sentEmailCount incremented by one
		try {
			visitUrl(SENT_URL);
			return (sentEmailCount+1 == findMessageCount());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkInitialState() {
		//check if account is in initial state by finding "inbox (selected element)"
		String checkUrl = driver.getCurrentUrl();
		return checkUrl.equals(INBOX_URL);
	}
	
	public void resetInitialState() {
		//reset initial state, go to INBOX_URL
		visitUrl(INBOX_URL);
	}
	
	//Get & Set Methods
	
	public void visitUrl(String url) {
		this.currentUrl = url;
		driver.get(url);
	}
	
	public String getCurrentUrl() {
		return currentUrl;
	}
	
	public void setFileName(String fileName) {
		this.storedFileName = fileName;
	}
	
	public String getFileName() {
		return storedFileName;
	}
	
	public void setRecipientEmail(String recipientEmail) {
		this.storedRecipientEmail = recipientEmail;
	}
	
	public String getRecipientEmail() {
		return storedRecipientEmail;
	}
	
	public boolean getSignedIn() {
		return signedIn;
	}
	
	public int getSentEmailCount() {
		return sentEmailCount;
	}
	
	//Helper Functions
	
	private void setupWebDriver() {
		try {
			if (driver == null) {
				System.out.println("Finding local chromedriver... ");
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				driver = new ChromeDriver();
                System.out.print("Chrome driver setup done!\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private void incrementSentEmailCount() {
		this.sentEmailCount++;
	}
	
	private int findMessageCount() {
		//Finds message count span and gets text
		WebElement msgCountSpan = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(MSG_COUNT_SPAN)));
		return Integer.valueOf(msgCountSpan.getText());
	}
	
}
