package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class Logger {

    @Step("{message}")
    public static void logStep(String message) {
	String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS a").format(new Date());
	System.out.println("<" + timeStamp + "> " + message);
	ExtentReport.info(message);
    }

    public static void logMessage(String message) {
	String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS a").format(new Date());
	System.out.println("<" + timeStamp + "> " + message);
	ExtentReport.info(message);
    }

    @Attachment(value = "Full Page Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllureReport(WebDriver driver) {
	return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static Media attachScreenshotToExtentReport(WebDriver driver) {
	return MediaEntityBuilder.createScreenCaptureFromBase64String(
		((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64), "Full Page Screenshot").build();
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
	String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	TakesScreenshot ts = (TakesScreenshot) driver;
	File source = ts.getScreenshotAs(OutputType.FILE);
	String destination = System.getProperty("user.dir") + "/src/test/resources/TestsScreenshots/" + screenshotName + dateName
		+ ".png";
	File finalDestination = new File(destination);
	FileUtils.copyFile(source, finalDestination);
	return destination;
    }

    @Attachment(value = "API Request - {type}", type = "text/json")
    public static byte[] attachApiRequest(String type, byte[] b) {
	return attachTextJson(b);
    }

    @Attachment(value = "API Response", type = "text/json")
    public static byte[] attachApiResponse(byte[] b) {
	return attachTextJson(b);
    }

//  @Attachment(type = "text/json")
    public static byte[] attachTextJson(byte[] b) {
	try {
	    return b;
	} catch (Exception e) {
	    return null;
	}
    }

}
