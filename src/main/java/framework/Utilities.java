package framework;

import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities {

	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;

	private static final Logger logger = LogManager.getLogger(Utilities.class);

	public static WebElement getElement(final String propertyName) {
		return driver.findElement(LocatorHelper.getLocator(prop.getProperty(propertyName)));
	}

	public static WebElement getElement(final WebElement element, final String propertyName) {
		return element.findElement(LocatorHelper.getLocator(prop.getProperty(propertyName)));
	}

	public static void waitTillElementIsLocated(final String propertyName) {
		wait.until(ExpectedConditions.presenceOfElementLocated(
				LocatorHelper.getLocator(prop.getProperty(propertyName))));
	}

	public static List<WebElement> getElements(final String propertyName) {
		return driver.findElements(LocatorHelper.getLocator(prop.getProperty(propertyName)));
	}

	public static boolean isFileRestored(List<WebElement> restoredElements,
			final String elementName, final String textToVerify) {
		for (WebElement webElement : restoredElements) {
			WebElement fileName = getElement(webElement, elementName);
			if (fileName.getText().equals(textToVerify)) {
				logger.info("Restored file found!");
				return true;
			}
		}
		return false;
	}

	public static boolean isFileAvailableInBin(List<WebElement> binElements,
			final String elementName, final String textToVerify) {
		for (WebElement webElement : binElements) {
			WebElement fileName = getElement(webElement, elementName);
			if (fileName.getText().equals(textToVerify)) {
				logger.info("File found in bin");
				getElement(webElement, "binfileoptions").click();
				return true;
			}
		}
		return false;
	}

	public static boolean isFileAvailable(List<WebElement> elements, final String elementName,
			final String textToVerify) {
		for (WebElement webElement : elements) {
			WebElement fileName = getElement(webElement, elementName);
			if (fileName.getText().equals(textToVerify)) {
				logger.warn("File not removed");
				return true;
			}
			logger.info(fileName.getText());
		}
		return false;
	}

	public static boolean isFileMovedToBin(List<WebElement> optionLayoutElements,
			final String elementName, final String textToVerify) {
		for (WebElement option : optionLayoutElements) {
			WebElement findElement = getElement(option, elementName);
			logger.info(findElement.getText());
			if (findElement.getText().equals(textToVerify)) {
				logger.info("Clicked");
				findElement.click();
				return true;
			}
		}
		return false;
	}

	public static boolean isFileCreated(List<WebElement> elements, final String elementName,
			final String textToVerify) {

		for (WebElement webElement : elements) {
			WebElement fileName = getElement(webElement, elementName);
			if (fileName.getText().equals(textToVerify)) {
				logger.info("File created!");
				fileName.click();
				return true;
			}
		}
		return false;
	}
}
