package gr.aueb.cf;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
//@Listeners(CustomTestNGListener.class)
public class HomeNavbarTest extends BaseTest {


    @Test(priority = 1)
    public void testRegisterButtonDropDown() throws InterruptedException {

//        ExtentTest test = ReportSetup.getDefaultExtent().createTest("Test Register Button"); //!!!
//        ExtentTest test = localExtentReport.createTest("Test register button");
        ExtentTest test = setupBeforeSuitReportingExtent().createTest("Test Register Button");

        try {
//            WebElement registerButton = navbarPage.findRegisterBtn();
//            registerButton.click();
            navbarPage.clickRegisterDropdn();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(navbarPage.getDropDown()));
            Assert.assertTrue(navbarPage.isDropDownRolesDisplayed(), "Dropdown is not displayed");//validation test success
            System.out.println("=====Dropdown displayed: " + navbarPage.isDropDownRolesDisplayed()+"=====");
            test.pass("Dropdown displayed successfully."); //send report details to selenium grid

            // Verify the number of dropdown items
            WebElement dropdownUl = dropdown.findElement(By.xpath("//ul[@class='dropdown-header ng-tns-c1967311527-0']"));
            List<WebElement> dropdownItems = dropdownUl.findElements(By.tagName("li"));
            Assert.assertEquals(dropdownItems.size(), 2, "Expected 2 items in the dropdown");
            System.out.println("dropdown elements number: " + dropdownItems.size());
            test.pass("---Dropdown contains the correct number of items: " + dropdownItems.size());//send report details to selenium grid

        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            Assert.fail("Test Register Button failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void testDropdownHoverEffect() throws InterruptedException {
//        ExtentTest test = ReportSetup.getDefaultExtent().createTest("Test Dropdown Hover Effect"); //!!!
//        ExtentTest test = localExtentReport.createTest("Test Dropdown Hover Effect");
        ExtentTest test = setupBeforeSuitReportingExtent().createTest("Test Dropdown Hover Effect");


        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            List<WebElement> dropdownLinks = navbarPage.getDropDown().findElements(By.tagName("a"));
            // Perform hover action and verify hover effect of each droped link item
            actions = new Actions(driver);
            for (WebElement link : dropdownLinks) {
                wait.until(ExpectedConditions.elementToBeClickable(link));
                actions.moveToElement(link).build().perform();
//                wait.until(ExpectedConditions.attributeToBe(link, "background-color", "rgba(231, 231, 231, 1)"));
                String backgroundColor = link.getCssValue("background-color");
                Assert.assertEquals(backgroundColor, "rgba(231, 231, 231, 1)", "Hover background color mismatch");
                test.pass("success report details to selenium grid:  Background-color on hover works");
                Reporter.log("background color -"+link.getAttribute("text")+"- : " + backgroundColor);
                System.out.println("background color -"+link.getAttribute("text")+"- : " + backgroundColor);
            }
        } catch (Exception e) {
//            test.fail("Test failed due to exception: " + e.getMessage());
            Reporter.log("Test Dropdown Hover Effect failed: " + e.getMessage());
            Assert.fail("Test Dropdown Hover Effect failed: " + e.getMessage());
            test.fail("Fail report details to selenium grid");
        }
    }

}
