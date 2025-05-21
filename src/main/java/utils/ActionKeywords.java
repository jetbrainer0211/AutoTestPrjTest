package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

public class ActionKeywords {
    public static WebDriver driver;

    public static void openBrowser(String browser) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest", "--disable-notifications");
        driver = new ChromeDriver(options);
    }

    public static void navigate(String url) {
        driver.get(url);
    }

    public static void inputText(String locatorType, String locatorValue, String data) {
        driver.findElement(getBy(locatorType, locatorValue)).sendKeys(data);
    }

    public static void click(String locatorType, String locatorValue) throws InterruptedException {
        driver.findElement(getBy(locatorType, locatorValue)).click();
    }

    public static void closeBrowser() {
        driver.close();
    }

    private static By getBy(String type, String value) {
        return switch (type.toLowerCase()) {
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "xpath" -> By.xpath(value);
            case "css" -> By.cssSelector(value);
            default -> throw new IllegalArgumentException("Invalid locator type: " + type);
        };
    }

    public static void verifyText(String locatorType, String locatorValue, String expectedText) {
        String actualText = driver.findElement(getBy(locatorType, locatorValue)).getText().trim();
        System.out.println(actualText);
        Assert.assertTrue(actualText.contains(expectedText));
    }
}
