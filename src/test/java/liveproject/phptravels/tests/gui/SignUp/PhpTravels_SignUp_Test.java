package liveproject.phptravels.tests.gui.SignUp;

import java.io.File;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import liveproject.phptravels.gui.pages.PhpTravels_Home_Page;
import utils.Logger;
import utils.BrowserFactory;
import utils.PropertiesReader;
import utils.Spreadsheet;
import utils.BrowserFactory.BrowserType;

@Epic("Live Project")
@Feature("PHPTravels Sign Up")
public class PhpTravels_SignUp_Test {
    WebDriver driver;
    Spreadsheet spreadSheet;
    String phptravelsHomePageURL = PropertiesReader.getProperty("liveproject.properties", "phptravels.home.url");
    Date date = new Date();;
    
    String firstName, lastName, mobileNumber, email, password;
    String currentTime = date.getTime() + "";

    @BeforeClass
    public void setUp() {
	spreadSheet = new Spreadsheet(new File("src/test/resources/TestData/LiveProject_PhpTravels_SignUp_TestData.xlsx"));
	spreadSheet.switchToSheet("testsheet2");
    }
    
    @BeforeMethod
    public void beforeMethod() {
	driver = BrowserFactory.openRemoteBrowser(BrowserType.FROM_PROPERTIES);
	driver.get(phptravelsHomePageURL);
    }

    @Test(description = "Valid User Sign Up")
    @Description("When I enter valid data in the sign up form And click the signup button, Then I should be registered successfully And be navigated to the user account page And I can see my user data and Hi message")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingValidUserSignUp() {
	firstName = spreadSheet.getCellData("FirstName", 2);
	lastName = spreadSheet.getCellData("LastName", 2);
	mobileNumber = spreadSheet.getCellData("Mobile Number", 2);
	email = spreadSheet.getCellData("Email", 2) + currentTime + "@test.com";
	Logger.logMessage("The mail is: " + email);
	password = spreadSheet.getCellData("Password", 2);

	String hiMessage = new PhpTravels_Home_Page(driver)
		.navigateToSignUpPage()
		.userSignUp(firstName, lastName, mobileNumber, email, password)
		.getHiMessage();
	Assert.assertEquals(hiMessage,  "Hi, " + firstName + " " + lastName);
    }
    
    @Test(description = "Invalid User Sign Up - Email Already Exists" , dependsOnMethods = {"testingValidUserSignUp"})
    @Description("Given i already signed up with an email, When I use the same email for new sign up , Then I should get an error message ")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingInvalidUserSignUp_emailAlreadyExists() {
	firstName = spreadSheet.getCellData("FirstName", 3);
	lastName = spreadSheet.getCellData("LastName", 3);
	mobileNumber = spreadSheet.getCellData("Mobile Number", 3);
	email = spreadSheet.getCellData("Email", 3) + currentTime + "@test.com";
	Logger.logMessage("The mail is: " + email);
	password = spreadSheet.getCellData("Password", 3);

	String alertMessage = new PhpTravels_Home_Page(driver)
		.navigateToSignUpPage()
		.invalidUserSignUp(firstName, lastName, mobileNumber, email, password)
		.getAlertMessage();
	Assert.assertEquals(alertMessage, spreadSheet.getCellData("Expected Alert Message", 3));
    }
    
    @Test(description = "Invalid User Sign Up - Wrong Email Format")
    @Description("When I use a wrong email format on the sign up , Then I should get an error message ")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingInvalidUserSignUp_emailWrongFormat() {
	firstName = spreadSheet.getCellData("FirstName", 3);
	lastName = spreadSheet.getCellData("LastName", 3);
	mobileNumber = spreadSheet.getCellData("Mobile Number", 3);
	email = spreadSheet.getCellData("Email", 3) + currentTime;
	Logger.logMessage("The mail is: " + email);
	password = spreadSheet.getCellData("Password", 3);

	String alertMessage = new PhpTravels_Home_Page(driver)
		.navigateToSignUpPage()
		.invalidUserSignUp(firstName, lastName, mobileNumber, email, password)
		.getAlertMessage();
	Assert.assertEquals(alertMessage, spreadSheet.getCellData("Expected Alert Message", 4));
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
	if (result.getStatus() == ITestResult.FAILURE) {
	    Logger.logMessage("The Test Case Failed!; Taking Screenshot....");
	    Logger.logScreenshot(driver);
	}
	driver.quit();
    }
}
