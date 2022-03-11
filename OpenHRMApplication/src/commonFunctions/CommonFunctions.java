package commonFunctions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class CommonFunctions {
	public static WebDriver driver=null;
	public static Properties properties=null;

	static Logger logger = Logger.getLogger(CommonFunctions.class);

	public Properties loadPropertyFile() throws IOException{
		FileInputStream fileInputStream= new FileInputStream("config.properties");
		properties= new Properties();
		properties.load(fileInputStream);
		return properties;

	}

	@BeforeSuite
	public void launchBrowser() throws IOException{
		PropertyConfigurator.configure("log4j.properties");
		logger.info("Orange HRM Test Begins");
		logger.info("Loading the property file");
		loadPropertyFile();

		String browser=properties.getProperty("browser");
		String url=properties.getProperty("url");
		//String driverLocation=properties.getProperty("DriverLocation");

		if(browser.equalsIgnoreCase("chrome")){
			//System.setProperty("webdriver.chrome.driver",driverLocation);
			logger.info("Launching Chrome");
			driver= new ChromeDriver();
		} else if(browser.equalsIgnoreCase("firefox")){
			logger.info("Launching Firefox");
			//System.setProperty("webdriver.gecko.driver",driverLocation);
			driver= new FirefoxDriver();
		}
		driver.manage().window().maximize();
		logger.info("Navigating to Application");
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	@AfterSuite
	public void tearDown(){
		logger.info("Execution done. Closing the browser");
		driver.quit();

	}


}
