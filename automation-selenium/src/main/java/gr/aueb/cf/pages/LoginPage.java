package gr.aueb.cf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //    By usernameLocator = By.xpath("//input[@id='username']");
//    By passwordLocator = By.xpath("//input[@id='password']");
//    By submitLocator = By.cssSelector("button[type='submit']");
    By authenticateLogin = By.cssSelector("#authenticate");
    By loginLocator = By.cssSelector("#login");

    @FindBy(xpath = "//input[@id='username']")
    WebElement username;
    @FindBy(xpath = "//input[@id='password']")
    WebElement password;
    @FindBy(css = "#authenticate" )
    WebElement authenticationBtn;
    @FindBy(css = "#login")
    WebElement loginNav;
    public void  sendUsername(String usernameTxt) {username.sendKeys(usernameTxt);}
    public void sendPassword(String passwordTxt){
        password.sendKeys(passwordTxt);
    }
    public void navigateToLogin(){
        loginNav.click();
    }
    public void clickLogin(){
        authenticationBtn.click();
    }

}
