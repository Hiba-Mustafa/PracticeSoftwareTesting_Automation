package io.PaySky.pages.utiles;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;

public class ScreenShotUtils {

    public static void takeScreenShotForElement(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("src/main/resources/" + name + ".png");
            FileUtils.copyFile(src, dest);
            AllureUtils.attachScreenshotstoAllure(name, dest.getPath());
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }

    }
    @Attachment(value = "{name}", type = "image/png")
    public static byte[] takeScreenShot(WebDriver driver, String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
