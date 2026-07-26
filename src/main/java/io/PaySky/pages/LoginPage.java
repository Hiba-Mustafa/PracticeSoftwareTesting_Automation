package io.PaySky.pages;

import io.PaySky.pages.utiles.JsonReader;
import io.PaySky.pages.utiles.RandomData;
import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.PaySky.pages.utiles.Waits.waitForClickable;
import static io.PaySky.pages.utiles.Waits.waitForVisibility;

public class LoginPage {

    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By submitLogin = By.cssSelector("input[data-test='login-submit']");
    private final By loginError = By.cssSelector("div[data-test='login-error']");
    private final By categories = By.cssSelector("button[data-test='nav-categories']");

    private static WebDriver driver;
    private final JsonReader testUser;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.testUser = new JsonReader("users"); // reads test-data/data.json
    }

    public void loginWith() {
        waitForVisibility(driver, emailField).sendKeys(RandomData.getLastEmail());
        driver.findElement(passwordField).sendKeys(RandomData.getLastPassword());
        waitForClickable(driver, submitLogin).click();
    }


    public void loginWithJsonUser(int index, String email, String password) {
        waitForVisibility(driver, emailField).sendKeys(testUser.getUserData(index, email));
        driver.findElement(passwordField).sendKeys(testUser.getUserData(index, password));
        waitForClickable(driver, submitLogin).click();
        System.out.println(driver.getCurrentUrl());
        try {
            System.out.println(getLoginErrorText());
        } catch (Exception e) {
            System.out.println("No login error displayed.");
        }
    }

    public void loginWithInvalidCredentials() {
        waitForVisibility(driver, emailField).sendKeys(testUser.getJsonData("$.invalidUser.email"));
        driver.findElement(passwordField).sendKeys(testUser.getJsonData("$.invalidUser.password"));
        driver.findElement(submitLogin).click();
        System.out.println(driver.getCurrentUrl());
        try {
            System.out.println(getLoginErrorText());
        } catch (Exception e) {
            System.out.println("No login error displayed.");
        }
    }

    public void loginWrongCredentials() {
        eraseText();
        waitForVisibility(driver, emailField).sendKeys(testUser.getUserData(2, "$.validUsers.email"));
        driver.findElement(passwordField).sendKeys(testUser.getJsonData("$.invalidUser.password"));
        driver.findElement(submitLogin).click();
    }

    public By validationMessage(String message) {
        return By.xpath("//div[normalize-space()='" + message + "']");
    }

    public String getValidationMsg(String message) {
        return Waits.waitForVisibility(driver,
                        validationMessage(message))
                .getText();
    }

    public void eraseText() {
        WebElement email = waitForVisibility(driver, emailField);
        WebElement password = driver.findElement(passwordField);

        if (!email.getAttribute("value").isEmpty()) {
            email.clear();
        }

        if (!password.getAttribute("value").isEmpty()) {
            password.clear();
        }
    }

    public String getLoginErrorText() {
        return waitForVisibility(driver, loginError).getText();
    }

    public Boolean isLoggedIn(String url) {
        return driver.getCurrentUrl().contains(url);
    }

    public Boolean successfuLogin(String url, By locator) {
        return driver.getCurrentUrl().contains(url) && driver.findElement(locator).isDisplayed();
    }

}
