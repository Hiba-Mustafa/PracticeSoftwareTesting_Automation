package io.PaySky.pages.utiles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class Waits {

    WebDriver driver;

    public Waits(WebDriver driver)
    {
        this.driver = driver;
    }

    public static WebElement waitForVisibility(WebDriver driver, By locator)
    {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator)
    {
        return new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForValueToAppear(WebDriver driver, String url)
    {
         new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(url));
    }

    public static void waitForElementToDisappear(WebDriver driver, By locator)
    {
        new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static <T> T waitFor(WebDriver driver, Function<WebDriver, T> condition) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(condition);
    }
}
