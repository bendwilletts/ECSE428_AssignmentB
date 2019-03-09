

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
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
	private String storedFilePath;
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
	private final String EMAIL_ELEMENT = "tr.zA.yO.byw";
	private final String COMPOSE_BUTTON = "div.T-I.J-J5-Ji.T-I-KE.L3";
	private final String TO_TEXT_AREA = "textarea[name='to']";
	private final String CC_BUTTON = "span[aria-label='Add Cc recipients ‪(⌘⇧C)‬']";
	private final String CC_TEXT_AREA = "textarea[name='cc']";
	private final String SUBJECT_TEXT_AREA = "input[name='subjectbox']";
	private final String MESSAGE_TEXT_AREA = "div[aria-label='Message Body']";
	private final String SEND_BUTTON = "div[aria-label='Send ‪(⌘Enter)‬']";
	private final String ATTACH_BUTTON = "div.a1.aaA.aMZ";
	private final String ATTACH_LINK = "a.dO";
	private final String NOT_RECOGNISED_ERROR_BUTTON = "button.J-at1-auR";
	private final String NOT_RECOGNISED_ERROR = "span.Kj-JD-K7-K0";
	
	private final String MESSAGE_SENT_NOTIFICATION = "span.ag.a8k";
	private final String ERROR_MESSAGE = "Error";

	
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
			updateSentEmailCount();
			resetInitialState();
			signedIn = true;
			return true;
		} catch (Exception e) {
			System.out.println("Sign In Failed!");
			return false;
		}
	}
	
	public boolean sendEmailWithImage() {
		//Send email with an image attachment to a recipient
		return sendEmailWithImage(storedRecipientEmail, storedFilePath);
	}
	
	public boolean sendEmailWithImage(String recipientEmail, String filePath) {
		//Send email with an image attachment to a recipient
		try {
			updateSentEmailCount();
			resetInitialState();
			clickComposeButton();
			enterRecipientEmail(recipientEmail);
			attachFile(filePath);
			clickSendButton();
			return true;
		} catch (Exception e) {
			if (e instanceof UnhandledAlertException) {
				return true;
			}
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean sendEmailWithImageAndCC(String recipientEmail, String filePath, String ccEmail) {
		//Send email with an image attachment to a recipient
		try {
			updateSentEmailCount();
			resetInitialState();
			clickComposeButton();
			clickCCButton();
			enterRecipientEmail(recipientEmail);
			enterCCEmail(ccEmail);
			attachFile(filePath);
			clickSendButton();
			return true;
		} catch (Exception e) {
			if (e instanceof UnhandledAlertException) {
				return true;
			}
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean sendEmailWithImage(String recipientEmail, String filePath, String subject, String message) {
		//Send email with an image attachment to a recipient including subject and message
		try {
			updateSentEmailCount();
			resetInitialState();
			clickComposeButton();
			enterRecipientEmail(recipientEmail);
			enterSubjectAndMessage(subject, message);
			attachFile(filePath);
			clickSendButton();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void clickComposeButton() {
		WebElement composeButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(COMPOSE_BUTTON)));
		composeButton.click();
	}
	
	public void clickCCButton() {
		WebElement ccButton = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CC_BUTTON)));
		ccButton.click();
	}
	
	public void enterRecipientEmail(String recipientEmail) {
		WebElement toTextArea = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(TO_TEXT_AREA)));
		toTextArea.sendKeys(recipientEmail);
	}
	
	public void enterCCEmail(String ccEmail) {
		WebElement ccTextArea = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CC_TEXT_AREA)));
		ccTextArea.sendKeys(ccEmail);
	}
	
	public void enterSubjectAndMessage(String subject, String message) {
		WebElement subjectTextArea = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(SUBJECT_TEXT_AREA)));
		subjectTextArea.sendKeys(subject);
		
		WebElement messageTextArea = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(MESSAGE_TEXT_AREA)));
		messageTextArea.sendKeys(message);
	}
	
	public void attachFile(String filePath) {
		try {
		driver.findElement(By.cssSelector(ATTACH_BUTTON)).click();
		Runtime.getRuntime().exec("osascript " + "src/main/java/attachfile_mac.scpt " + filePath);
		(new WebDriverWait(driver, 20))
        .until(ExpectedConditions.elementToBeClickable(By.cssSelector(ATTACH_LINK)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void clickSendButton() {
		try {
			WebElement sendButton = driver.findElement(By.cssSelector(SEND_BUTTON));
			sendButton.click();
			(new WebDriverWait(driver, 10))
	        .until(ExpectedConditions.or(
	        		ExpectedConditions.elementToBeClickable(By.cssSelector(MESSAGE_SENT_NOTIFICATION)),
	        		ExpectedConditions.elementToBeClickable(By.cssSelector(NOT_RECOGNISED_ERROR_BUTTON))));
		} catch (UnhandledAlertException uae) {
			acceptPrompt();
			(new WebDriverWait(driver, 10))
	        .until(ExpectedConditions.or(
	        		ExpectedConditions.elementToBeClickable(By.cssSelector(MESSAGE_SENT_NOTIFICATION)),
	        		ExpectedConditions.elementToBeClickable(By.cssSelector(NOT_RECOGNISED_ERROR_BUTTON))));
		}
	}
	
	public void updateSentEmailCount() {
		//visit SENT_URL and obtains sent email count
		try {
			visitUrl(SENT_URL);
			sentEmailCount = findMessageCount();
			System.out.println("Sent Email Count = " + sentEmailCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean confirmSentEmailCount() {
		//visit SENT_URL and checks if sentEmailCount incremented by one
			visitUrl(SENT_URL);
			return (sentEmailCount+1 == findMessageCount());
	}
	
	public boolean confirmNotRecognisedError() {
		String errorMessage = driver.findElement(By.cssSelector(NOT_RECOGNISED_ERROR)).getText();
		return(errorMessage.equals(ERROR_MESSAGE));
	}
	
	public boolean checkInitialState() {
		//check if account is in initial state by finding "inbox (selected element)"
		String checkUrl = driver.getCurrentUrl();
		return checkUrl.equals(INBOX_URL);
	}
	
	public void resetInitialState() {
		//reset initial state, go to INBOX_URL
		visitUrl(INBOX_URL);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains("Inbox"));
	}
	
	public void shutdownDriver() {
		driver.quit();
	}
	
	//Get & Set Methods
	
	public void visitUrl(String url) {
		this.currentUrl = url;
		driver.get(url);
	}
	
	public String getCurrentUrl() {
		return currentUrl;
	}
	
	public void setFilePath(String filePath) {
		this.storedFilePath = filePath;
	}
	
	public String getFilePath() {
		return storedFilePath;
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
	
	private int findMessageCount() {
		//Finds message count span and gets text
		(new WebDriverWait(driver, 10))
                .until(ExpectedConditions.titleContains("Sent Mail"));
		List<WebElement> sentMessages = driver.findElements(By.cssSelector(EMAIL_ELEMENT));
		System.out.println("Sen messages:"  + sentMessages);
		return sentMessages.size();
	}
	
	private void acceptPrompt() {
		driver.switchTo().alert().accept();
	}
}
