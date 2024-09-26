package gr.aueb.cf;


import com.aventstack.extentreports.ExtentReports;
import gr.aueb.cf.pages.NavbarPage;
import gr.aueb.cf.setup.ReportSetup;
import gr.aueb.cf.setup.Setup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest extends ReportSetup {
    WebDriver driver;
    NavbarPage navbarPage;
    ExtentReports localExtentReport;
    Actions actions;
    //    private static final String DRIVER_PATH = "C:\\Users\\User\\Νέος φάκελος\\chromedriver.exe";
    private static final String REPORT_PATH = "C:\\Users\\User\\Desktop\\books\\springboot\\exchange-book\\TestReports\\new.html";
    @BeforeClass
    public void setUp(){
        Setup.initProperties();
        driver = Setup.initDriver();
        navbarPage = new NavbarPage(driver);
    }

    @BeforeSuite//(appropriate override in every test class), ensures that all tests run in a shared report suit with this extent instance
    public ExtentReports setupBeforeSuitReportingExtent() {
        localExtentReport = ReportSetup.getDefaultExtent();
        return localExtentReport;
    }

    @AfterSuite
    public void tearDownReporting() {
//     if(ReportSetup.getDefaultExtent() != null){
//         ReportSetup.getDefaultExtent().flush(); // !!!
//     }
        if(localExtentReport != null){
            localExtentReport.flush();
        }
    }

    @AfterClass
    public void tearDown()  {
        if (driver != null) {
            driver.quit();
        }
    }
}
