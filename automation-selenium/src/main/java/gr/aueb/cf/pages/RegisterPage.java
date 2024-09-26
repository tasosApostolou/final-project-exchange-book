package gr.aueb.cf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegisterPage {
    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//button[normalize-space()='REGISTER']")
    WebElement registerBtn;

    @FindBy(xpath = "//div[@class='mat-mdc-menu-content ng-tns-c1967311527-0']")
    WebElement dropdown;
    @FindBy(xpath = "//ul[@class='dropdown-header ng-tns-c1967311527-0']")
    WebElement drobdownUl;

    @FindBy(xpath = "//input[@id='mat-input-0']")
    WebElement inputFirstname;

    @FindBy(xpath = "//input[@id='mat-input-1']")
    WebElement inputLastname;

    @FindBy(xpath = "//input[@id='mat-input-2']")
    WebElement inputUsername;

    @FindBy(xpath = "//input[@id='mat-input-3']")
    WebElement inputPassword;

    @FindBy(xpath = "//input[@id='mat-input-4']")
    WebElement inputConfirmPassword;

    @FindBy(css = ".mdc-button__label")
    WebElement registerNewPerson;

    @FindBy(xpath = "//span[normalize-space()='Success: User register succesfully']")
    WebElement successMessage;




    public void clickRegisterDropdn() {
        registerBtn.click();
    }

    public Boolean isDropDownRolesDisplayed() {
        return dropdown.isDisplayed();
    }
    public WebElement getPersonLink(){
//        return dropdown.findElement(By.xpath(".//a[2]"));
        return dropdown.findElement(By.xpath("//a[contains(text(),'Εγγραφη προσωπικου χρηστη')]"));

    }

    public void clickToRegisterPerson(){ //clickable navigate to register person by navbar
        registerPersonDropdownLink().click();
    }
    public void clickToRegisterStore(){ //clickable navigate to register store by navbar
        registerStoreDropdownLink().click();
    }

    public WebElement getDropDown(){
        return dropdown;
    }
    public void navigateToRegisterPerson(){
        try{
            driver.get("http://localhost:4200/user-registration");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<WebElement> getDropDownItemLinks(){
        List<WebElement> dropDownLinks = drobdownUl.findElements(By.tagName("a"));
        return dropDownLinks;
    }

    public WebElement registerPersonDropdownLink(){
        return getDropDownItemLinks().get(0);
    }
    public WebElement registerStoreDropdownLink(){

        return getDropDownItemLinks().get(1);
    }

    public void registerPerson(String firstname,String lastname,String username, String password, String confirmPassword){
        inputFirstname.sendKeys(firstname);
        inputLastname.sendKeys(lastname);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        inputConfirmPassword.sendKeys(confirmPassword);
        registerNewPerson.click();

    }

    public boolean isRegisterSuccess(){
        return successMessage.isDisplayed();
    }

}
