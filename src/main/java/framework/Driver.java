package framework;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Driver {

	public static WebDriver driver;

	public static WebDriver getInstance(final DesiredCapabilities capabilities)
			throws MalformedURLException {
		if (driver == null) {
			return new RemoteWebDriver(new URL(Utilities.prop.getProperty("url")), capabilities);
		}
		return driver;
	}

}
