package gr.aueb.cf.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavbarPage {
    WebDriver driver;

    public NavbarPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[normalize-space()='REGISTER']")
    WebElement registerBtn;
    @FindBy(xpath = "//div[@class='mat-mdc-menu-content ng-tns-c1967311527-0']")
    WebElement dropdown;

    public WebElement getDropDown(){
        return dropdown;
    }
    public void clickRegisterDropdn() {
        registerBtn.click();
    }

    public Boolean isDropDownRolesDisplayed() {
        return dropdown.isDisplayed();
    }

    public LoginPage navigateToLoginPage() {
        return new LoginPage(driver);
    }

    public String homeNavbarTitle() {
        return driver.getTitle();
    }
//    public getDropDown(){}
//
//}
}