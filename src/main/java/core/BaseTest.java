package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    WebDriver driver; // tạo driver

    private enum BROWSER {
        CHROME, FIREFOX, SAFARI, MICROSOFTEDGE
    }

    @Parameters({"browserName"})
    @BeforeMethod
    public void beforeTest(String browserName) {

        BROWSER browser = BROWSER.valueOf(browserName.toUpperCase());
        if (browser == BROWSER.SAFARI) {
            driver = new SafariDriver();
        } else if (browser == BROWSER.CHROME) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--guest"); // Optional
            chromeOptions.addArguments("----disable-save-password-bubble");

            String downloadPath = System.getProperty("user.dir") + "/downloads";
            Map<String, Object> prefs = new HashMap<>();
            //quyen guest
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);

            //download file k co save as
            prefs.put("download.default_directory", downloadPath);
            prefs.put("download.prompt_for_download", false);

            chromeOptions.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver((chromeOptions));
        } else if (browser == BROWSER.MICROSOFTEDGE) {
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Please enter correct browser name");
        }

    }

    @AfterMethod
    public void afterTest() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
