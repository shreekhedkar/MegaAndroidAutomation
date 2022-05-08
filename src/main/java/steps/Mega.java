package steps;

import static framework.Utilities.driver;
import static framework.Utilities.getElement;
import static framework.Utilities.getElements;
import static framework.Utilities.isFileAvailable;
import static framework.Utilities.isFileAvailableInBin;
import static framework.Utilities.isFileCreated;
import static framework.Utilities.isFileMovedToBin;
import static framework.Utilities.isFileRestored;
import static framework.Utilities.prop;
import static framework.Utilities.wait;
import static framework.Utilities.waitTillElementIsLocated;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.Driver;
import framework.Utilities;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Mega {

	private static final Logger logger = LogManager.getLogger(Utilities.class);

	@Given("^launch mega android application$")
	public void launch_mega_android_application() throws Throwable {
	}

	@Given("^provide login credentials$")
	public void provide_login_credentials() throws Throwable {
		getElement("loginbutton").click();
		getElement("email").sendKeys(prop.getProperty("inputusername"));
		getElement("password")
				.sendKeys(new String(Base64.decodeBase64(prop.getProperty("inputpassword"))));
		getElement("login").click();
	}

	@Then("^user is logged in$")
	public void user_is_logged_in() throws Throwable {
		waitTillElementIsLocated("clouddrive");
	}

	@Then("^create text file in cloud drive$")
	public void create_text_file_in_cloud_drive() throws Throwable {
		getElement("clouddrive").click();
		getElement("floatingbutton").click();

		waitTillElementIsLocated("newtextfileoption");
		getElement("newtextfileoption").click();

		waitTillElementIsLocated("typefilename");
		getElement("typefilename").sendKeys("a.txt");

		getElement("createbutton").click();

		waitTillElementIsLocated("edittext");
		getElement("edittext").sendKeys("Mega Testing");

		getElement("save").click();

		waitTillElementIsLocated("filelistlayout");
		List<WebElement> elements = getElements("filelistlayout");

		Assert.assertTrue(isFileCreated(elements, "filename", prop.getProperty("inputfilename")));
	}

	@Given("^mega android application$")
	public void mega_android_application() throws Throwable {
	}

	@When("^file is moved to rubbish bin$")
	public void file_is_moved_to_rubbish_bin() throws Throwable {
		Thread.sleep(3000);
		getElement("fileoptions").click();
		waitTillElementIsLocated("fileoptionslayout");

		List<WebElement> optionLayoutElements = getElements("fileoptionslayout");
		Assert.assertTrue(
				isFileMovedToBin(optionLayoutElements, "filename", "Move to Rubbish Bin"));

	}

	@Then("^file is deleted$")
	public void file_is_deleted() throws Throwable {
		getElement("movebutton").click();
		waitTillElementIsLocated("fileoptionslayout");
		List<WebElement> elements = getElements("filelistlayout");

		Assert.assertFalse(
				isFileAvailable(elements, "filename", prop.getProperty("inputfilename")));

		getElement("menuslider").click();
		getElement("rubbishbin").click();

		List<WebElement> binElements = getElements("filelistlayout");

		Assert.assertTrue(
				isFileAvailableInBin(binElements, "filename", prop.getProperty("inputfilename")));
	}

	@When("^file is restored from rubbish bin$")
	public void file_is_restored_from_rubbish_bin() throws Throwable {
		waitTillElementIsLocated("restore");
		getElement("restore").click();
		waitTillElementIsLocated("backfrombin");
		getElement("backfrombin").click();

	}

	@Then("^file is restored back to cloud drive$")
	public void file_is_restored_back_to_cloud_drive() throws Throwable {
		List<WebElement> restoredElements = getElements("filelistlayout");

		Assert.assertTrue(
				isFileRestored(restoredElements, "filename", prop.getProperty("inputfilename")));
	}

	@BeforeAll
	public static void init() throws IOException {

		Utilities.prop = new Properties();
		InputStream inStream = new FileInputStream(
				"C:\\EclipseWs\\mega-android\\src\\main\\resources\\Locators.properties");
		prop.load(inStream);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		logger.info("Initializing the browser...");
		capabilities.setCapability("platformName", prop.getProperty("platformname"));
		capabilities.setCapability("deviceName", prop.getProperty("devicename"));
		capabilities.setCapability("--session-override", true);
		capabilities.setCapability("appPackage", prop.getProperty("apppackage"));
		capabilities.setCapability("appActivity", prop.getProperty("appactivity"));
		capabilities.setCapability("autoGrantPermissions", "true");

		driver = Driver.getInstance(capabilities);
		wait = new WebDriverWait(driver, 30);
	}

	@AfterStep
	public void addScreenshot(Scenario scenario) {

		final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot, "image/png", "image");
	}

	@After
	public void tearDown() {
		Utilities.driver.quit();
	}
}
