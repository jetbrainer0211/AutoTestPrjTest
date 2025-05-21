package core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class BasePage {
    protected WebDriver driver;
    WebDriverWait explicitWait;
    Actions actions;
    JavascriptExecutor javascriptExecutor;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        javascriptExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return explicitWait;
    }

    public void sleepInSecond(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /* ----------------------- Wait Methods ---------------------- */
    public WebElement waitForElementVisible(WebDriver driver, By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementVisible(WebDriver driver, WebElement element) {
        return explicitWait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementClickable(WebDriver driver, By locator) {
        return explicitWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementClickable(WebDriver driver, WebElement element) {
        return explicitWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /* ----------------------- Action Methods ---------------------- */
    public void clickToElement(WebDriver driver, By locator) {
        WebElement element = waitForElementClickable(driver, locator);
        element.click();
    }

    public void clickToElement(WebDriver driver, WebElement element) {
        waitForElementClickable(driver, element);
        element.click();
    }

    public void sendKeyToElement(WebDriver driver, By locator, String value) {
        WebElement element = waitForElementVisible(driver, locator);
        element.clear();
        element.sendKeys(value);
    }

    public void sendKeyToElement(WebDriver driver, WebElement element, String value) {
        waitForElementVisible(driver, element);
        element.clear();
        element.sendKeys(value);
    }

    public String getElementText(WebDriver driver, By locator) {
        WebElement element = waitForElementVisible(driver, locator);
        return element.getText().trim();
    }

    public String getElementText(WebDriver driver, WebElement element) {
        waitForElementVisible(driver, element);
        return element.getText().trim();
    }

    public boolean isDisplayElement(WebDriver driver, By locator) {
        WebElement element = waitForElementVisible(driver, locator);
        return element.isDisplayed();
    }

    public boolean isDisplayElement(WebDriver driver, WebElement element) {
        waitForElementVisible(driver, element);
        return element.isDisplayed();
    }

    public void hoverToElement(WebDriver driver, By locator) {
        WebElement element = waitForElementVisible(driver, locator);
        actions.moveToElement(element).perform();
    }

    public void hoverToElement(WebDriver driver, WebElement element) {
        waitForElementVisible(driver, element);
        actions.moveToElement(element).perform();
    }

    public void clickToElementByJS(WebDriver driver, By locator) {
        WebElement element = waitForElementClickable(driver, locator);
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }

    public void clickToElementByJS(WebDriver driver, WebElement element) {
        waitForElementClickable(driver, element);
        javascriptExecutor.executeScript("arguments[0].click();", element);
    }

    /* ----------------------- Browser/Window Methods ---------------------- */
    public void navigateToPage(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    public void switchToNewWindow(WebDriver driver) {
        String mainWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public static String generateRandomString() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }
        return randomString.toString();
    }

    public void setSliderValue(WebDriver driver, WebElement slider, String value) {
        waitForElementVisible(driver, slider);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input'));" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                slider, value
        );
    }
}
