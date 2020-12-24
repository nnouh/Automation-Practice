package examples.testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;

public class TestngXmlGroupsDemo {

    WebDriver driver;

    @BeforeTest(groups = { "Regression", "Smoke" })
    public void setup() {
	System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/windows-64/chromedriver.exe");

	driver = new ChromeDriver();
	driver.manage().window().maximize();

	driver.get("https://www.google.com/ncr");
    }

    @Test(groups = { "Regression" })
    public void getTitle() {
	System.out.println("Page Title: " + driver.getTitle());
	driver.getTitle();
    }

    @Test(groups = { "Smoke" })
    public void getURL() {
	System.out.println("Page URL: " + driver.getCurrentUrl());
    }

    @AfterTest(groups = { "Regression", "Smoke" })
    public void quit() {
	driver.quit();
    }

}
