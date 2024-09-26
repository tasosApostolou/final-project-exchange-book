package gr.aueb.cf.setup;

import com.aventstack.extentreports.ExtentReports;
import gr.aueb.cf.setup.ReportSetup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Setup {
    private static  WebDriver driver;
    private static Properties prop;

//    public static ExtentReports getDefaultExtentReports(){
//        return ReportSetup.getDefaultExtent();
//    }
    public static WebDriver initDriver() {
        String browserName = prop.getProperty("browser").toUpperCase();
//        WebDriverManager.getInstance(DriverManagerType.valueOf(browserName)).setup();
        // launch appropriate browser
        switch (browserName) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "SAFARI":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "IEXPLORER":
                WebDriverManager.chromedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            default:
                System.out.println("=======invalid browser=====");
        }


        // initiate Event Firing Driver so that it will capture all events
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));
        return driver;
    }
//    public static Properties initProperties() {
public static void initProperties() {

    prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(".\\src\\test\\resources\\config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
//        return prop;
    }

}
