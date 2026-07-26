package io.PaySky.pages;

import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class LogOutPage {

    private final By accountMenu = By.id("menu");
    private final By signOutLink = By.cssSelector("[data-test='nav-sign-out']");

    private WebDriver driver;

    public LogOutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void logout() {
        if (!driver.findElements(accountMenu).isEmpty()) {

            Waits.waitForClickable(driver, accountMenu).click();
            Waits.waitForClickable(driver, signOutLink).click();
        }
    }

    public Boolean isLoggedOut(String url) {
        return driver.getCurrentUrl().equals(url);
    }
}