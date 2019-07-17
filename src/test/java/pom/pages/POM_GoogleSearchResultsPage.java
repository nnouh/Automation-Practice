package pom.pages;


import java.io.IOException;
import org.openqa.selenium.By;
import org.testng.Assert;
import base.BaseClass;

public class POM_GoogleSearchResultsPage {

	//Variables

	// Element
	By google_result = By.xpath("(//h3[@class='LC20lb'])");

	// Methods
	public void assertResultText(int resultNumber, String expectedText) throws IOException {
		
		google_result = By.xpath("(//h3[@class='LC20lb'])" + "[" + resultNumber + "]");
		String SearchResultText = BaseClass.getWebDriver().findElement(google_result).getText();
		Assert.assertEquals(SearchResultText , expectedText);
	}

}
