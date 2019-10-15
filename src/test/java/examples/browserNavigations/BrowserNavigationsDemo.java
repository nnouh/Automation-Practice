package examples.browserNavigations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrowserNavigationsDemo {

	WebDriver driver;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/windows-64/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("http://toolsqa.com/");
	}

	@Test(priority = 1)
	public void backTest() throws InterruptedException {

		System.out.println("First URL: " + driver.getCurrentUrl());

		driver.navigate().to("http://toolsqa.com/selenium-tutorial/");
		System.out.println("After navigation URL: " + driver.getCurrentUrl());
		Thread.sleep(2000);
		driver.navigate().back();
		System.out.println("Back URL: " + driver.getCurrentUrl());
		Thread.sleep(2000);

	}

	@Test(priority = 2)
	public void forwardTest() throws InterruptedException {
		driver.navigate().forward();
		System.out.println("Forward URL: " + driver.getCurrentUrl());
		Thread.sleep(2000);

	}

	@Test(priority = 3)
	public void checkBoxesTest() throws InterruptedException {
		driver.navigate().refresh();
		System.out.println("Refresh URL: " + driver.getCurrentUrl());
		Thread.sleep(2000);

	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}