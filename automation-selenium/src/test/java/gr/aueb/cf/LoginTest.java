package gr.aueb.cf;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import gr.aueb.cf.pages.LoginPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest extends BaseTest   {
    LoginPage lp ;
    @BeforeSuite
    @Override
    public ExtentReports setupBeforeSuitReportingExtent() {
//        reportSetup = new ReportSetup();
//        ReportSetup rs = new ReportSetup();
        ExtentSparkReporter newPathReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\TestReports\\loginSpecificReport.html");
        newPathReporter.config().setReportName("New Login Report");
        newPathReporter.config().setDocumentTitle("Login report");
        localExtentReport = new ExtentReports(); // custom new specific report path and .html browser
        localExtentReport.attachReporter(newPathReporter);
        localExtentReport.setSystemInfo("Login","Specific");
        return localExtentReport;
    }

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        lp = new LoginPage(driver);

        lp.navigateToLogin();
    }

    @Test(priority = 3)
    void testLogin(){
        ExtentTest test = setupBeforeSuitReportingExtent().createTest("Login test");
//        ExtentTest test = localExtentReport.createTest("Login test");

        try {
            lp.sendUsername("usernameTest");
            lp.sendPassword("P@s$w0rd!");
            lp.clickLogin();
            Thread.sleep(5000);

            test.pass("Successfully send keys login button.");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

//            Thread.sleep(3000);
            System.out.println("keys sent successfully");
        }catch (Exception e){
            System.out.println("login failure");
            test.fail("failure");
            test.fail("FAILURE LOGIN: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @AfterSuite
    @Override
    public void tearDownReporting() {
//        if (this.setupBeforeSuitReportingExtent() != null) {
//            this.setupBeforeSuitReportingExtent().flush();
//        }
        if (localExtentReport != null){
            localExtentReport.flush();
        }
    }
}
