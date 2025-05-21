package core;


import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Listener extends BaseTest implements ITestListener {
    WebDriver driver= getDriver();
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("onTest "+getTestMethodName(result)+ " start");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess "+getTestMethodName(result)+ " succeed \n");

    }

//    @Override
//    public void onTestFailure(ITestResult result) {
//        System.out.println("onTestFail "+getTestMethodName(result)+ " failed \n");
//        try {
//            Robot robot = new Robot();
//            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
//
//            Random random = new Random();
//            int randomNumber = random.nextInt(1000); // Adjust the range as needed
//
//            // Create the file name with the random number
//            String fileName = String.format("fail_%03d.png", randomNumber);
//
//
//            File screenshotFile = new File("src/main/resources/screenshots/", fileName);
//            ImageIO.write(screenFullImage, "png", screenshotFile);
//
//            System.out.println("A full screenshot saved!");
//        } catch (AWTException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFail "+getTestMethodName(result)+ " failed \n");
        Object currentClass = result.getInstance();
        WebDriver webDriver = ((BaseTest) currentClass).driver;
        try {
            Random random = new Random();
            int randomNumber = random.nextInt(1000);
            String fileName = String.format("fail_%03d.png", randomNumber);
            File screenshotFile = new File("src/main/resources/screenshots/", fileName);
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(webDriver);
            ImageIO.write(screenshot.getImage(),"PNG",screenshotFile);
            System.out.println("A full screenshot saved!");

            // 🛑 Attach vào Allure Report
            attachScreenshotToAllure(screenshot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Attachment(value = "Full Page Screenshot", type = "image/png")
    private byte[] attachScreenshotToAllure(Screenshot screenshot) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("onTestSkipped "+getTestMethodName(result)+ " skipped \n");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailed in success percentage "+getTestMethodName(result));
    }


    private static String getTestMethodName(ITestResult iTestResult){
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
}
