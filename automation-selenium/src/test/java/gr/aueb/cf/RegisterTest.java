package gr.aueb.cf;


import com.aventstack.extentreports.ExtentTest;

import gr.aueb.cf.pages.RegisterPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

//@Listeners(CustomTestNGListener.class)
public class RegisterTest extends BaseTest {

    RegisterPage registerPage;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        registerPage = new RegisterPage(driver);
//        registerPage.navigateToRegisterPerson();
    }
    @Test(priority = 4)
    public void navigateToRegisterPerson() {
//        ExtentTest test = ReportSetup.getDefaultExtent().createTest("Test2 Register page");
//        ExtentTest test = localExtentReport.createTest("Test register page");
        ExtentTest test = setupBeforeSuitReportingExtent().createTest("Test register page");
        try {

            registerPage.clickRegisterDropdn();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(registerPage.getDropDown()));
            Assert.assertTrue(registerPage.isDropDownRolesDisplayed(), "Dropdown is not displayed");//validation test success
            System.out.println("Dropdown displayed: " + registerPage.isDropDownRolesDisplayed());
            test.pass("Dropdown displayed successfully."); //send report details to selenium grid

            registerPage.clickToRegisterPerson();
            boolean isNavigated = wait.until(ExpectedConditions.urlToBe("http://localhost:4200/user-registration"));
            Assert.assertTrue(isNavigated, "Can't navigate to register person clickable");
            test.pass(" Report details: clickable Navigating to register person works ");//send report details to selenium grid
            Reporter.log("clickable Navigating to register person works ");

            registerPage.registerPerson("testPersonNamea","testPersonLastnamea","usernameTest","P@s$w0rd!","P@s$w0rd!");
            Assert.assertTrue(registerPage.isRegisterSuccess(),"Register not Success");
            test.pass("user register succesfully");
        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            Assert.fail("Test Register  failed: " + e.getMessage());
        }
    }
}
