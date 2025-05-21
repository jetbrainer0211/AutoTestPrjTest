package pages;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[type='text']")
    private WebElement userName;

    @FindBy(css = "input[type='password']")
    private WebElement passWord;

    @FindBy(xpath = "(//button[@type='submit'])[1]")
    private WebElement loginBtn;


    public void login(String u, String p) {
        sendKeyToElement(getDriver(), userName, u);
        sendKeyToElement(getDriver(), passWord, p);
        clickToElement(getDriver(), loginBtn);
    }
}
