package nextlevel.liveproject.tests;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import nextlevel.liveproject.pages.GoogleHomePage;
import nextlevel.liveproject.utils.BrowserFactory;
import nextlevel.liveproject.utils.PropertiesReader;
import nextlevel.liveproject.utils.SpreadsheetUtil;
import nextlevel.liveproject.utils.AllureReport;
import nextlevel.liveproject.utils.BrowserFactory.BrowserType;

@Epic("Live Project")
@Feature("Google Search")
public class GoogleSearchTest {
    private WebDriver driver;
    SpreadsheetUtil spreadSheet;

    String googleHomePageURL = PropertiesReader.getProperty("liveproject.properties", "google.home.url");

    @BeforeClass
    public void setUp() {
	spreadSheet = new SpreadsheetUtil(new File("src/test/resources/TestData/LiveProject_TestData.xlsx"));
	spreadSheet.switchToSheet("testsheet2");
	driver = BrowserFactory.openBrowser(BrowserType.FROM_PROPERTIES);
	driver.get(googleHomePageURL);
    }

    @Test(description = "Validating the search function on Goole home page")
    @Story("Search Engine Story")
    @Description("Given I'm on the Google home page; When I search for a value in the search bar And click Enter; Then I should be navigated to the search results page And get the search results related to the search value entered")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("focus-case-1539798")
    @Issue("bug-tracker#1")
    public void testingGoogleSearch() {
	String searchIndex = spreadSheet.getCellData("Search Index", 1);
	String searchData = spreadSheet.getCellData("Search Data", 1);
	String expected = spreadSheet.getCellData("Expected", 1);

	new GoogleHomePage(driver)
		.googleSearch(searchData)
		.assertOnPageTitle(searchData)
		.assertOnSearchResult(expected, searchIndex);
    }

    @AfterMethod
    public void AfterMethod(ITestResult result) {
	if (result.getStatus() == ITestResult.FAILURE) {
	    AllureReport.logMessage("The Test Case Failed!; Taking Screenshot....");
	    AllureReport.logScreenshot(driver);
	}
    }

    @AfterClass
    public void closingBrowser() {
	driver.quit();
    }
}
