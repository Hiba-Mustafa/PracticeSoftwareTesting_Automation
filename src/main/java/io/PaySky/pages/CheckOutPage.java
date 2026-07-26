package io.PaySky.pages;

import io.PaySky.pages.utiles.JsonReader;
import io.PaySky.pages.utiles.RandomData;
import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckOutPage {

    private final By proceedSignIn = By.cssSelector("button[data-test='proceed-2']");
    private final By countryDropdown = By.id("country");
    private final By postalCodeField = By.id("postal_code");
    private final By houseNumberField = By.id("house_number");
    private final By street = By.id("street");
    private final By proceedAddress = By.cssSelector("button[data-test='proceed-3']");
    private final By paymentMethodDropdown = By.cssSelector("[data-test='payment-method']");
    private final By finishButton = By.cssSelector("button[data-test='finish']");
    private final By orderConfirmation = By.cssSelector("[data-test='payment-success-message']");
    private final By isLoggedIn = By.cssSelector("p[class='ng-star-inserted']");
    private final By creditCardNumber = By.id("credit_card_number");
    private final By expirationDate = By.id("expiration_date");
    private final By cvv = By.id("cvv");
    private final By creditCardName = By.id("card_holder_name");
    private final By guest = By.cssSelector("a[href='#guest-tab']");
    private final By guestEmail = By.id("guest-email");
    private final By guestFirstName = By.id("guest-first-name");
    private final By guestLastName = By.id("guest-last-name");
    private final By continueGuest = By.cssSelector("input[data-test='guest-submit']");
    private final By proceedToCheckout = By.cssSelector("[data-test='proceed-2-guest']");
    private final By proceedCheckLogin = By.cssSelector("[data-test='proceed-2']");
    private final By signIn = By.cssSelector("a[href='#signin-tab']");


    private final WebDriver driver;
    private final JsonReader testData;
    private final JsonReader paymentData;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        this.testData = new JsonReader("users");
        this.paymentData = new JsonReader("paymentData");
    }

    public void confirmAlreadyLoggedIn() {
        Waits.waitForClickable(driver, proceedSignIn).click();
    }

    public void fillAddress() {
        new Select(Waits.waitForVisibility(driver, countryDropdown)).selectByVisibleText(testData.getJsonData("$.registration.country"));
        driver.findElement(postalCodeField).sendKeys(testData.getJsonData("$.registration.postalCode"));
        driver.findElement(houseNumberField).sendKeys(testData.getJsonData("$.registration.houseNumber"));
        waitForAutoFilledFields();
    }

    public void waitForAutoFilledFields() {
        Waits.waitFor(driver, d -> !d.findElement(street).getAttribute("value").isEmpty());
    }

    public By validationMessage(String message) {
        return By.xpath("//div[normalize-space()='" + message + "']");
    }

    public String getValidationMsg(String message) {
        return Waits.waitForVisibility(driver,
                        validationMessage(message))
                .getText();
    }

    public void proceedToPayment() {
        Waits.waitForVisibility(driver, proceedAddress).click();
    }

    public void payWithCashOnDelivery() {
        new Select(Waits.waitForVisibility(driver, paymentMethodDropdown)).selectByValue("cash-on-delivery");
        Waits.waitForClickable(driver, finishButton).click();
    }

    public void payWithValidCreditCard() {
        new Select(Waits.waitForVisibility(driver, paymentMethodDropdown)).selectByValue("credit-card");
        Waits.waitForVisibility(driver, creditCardNumber).sendKeys(paymentData.getJsonData("$.creditCardNumber"));
        driver.findElement(expirationDate).sendKeys(paymentData.getJsonData("$.expirationDate"));
        driver.findElement(cvv).sendKeys(paymentData.getJsonData("$.cvv"));
        driver.findElement(creditCardName).sendKeys(paymentData.getJsonData("$.cardHolderName"));
        Waits.waitForClickable(driver, finishButton).click();
    }

    public void payWithInvalidCreditCard() {
        new Select(Waits.waitForVisibility(driver, paymentMethodDropdown)).selectByValue("credit-card");
        Waits.waitForVisibility(driver, creditCardNumber).sendKeys(paymentData.getJsonData("$.invalidCCN"));
        driver.findElement(expirationDate).sendKeys(paymentData.getJsonData("$.expirationDate"));
        driver.findElement(cvv).sendKeys(paymentData.getJsonData("$.cvv"));
        driver.findElement(creditCardName).sendKeys(paymentData.getJsonData("$.cardHolderName"));
    }

    public void proceedCheckOutAfterLogin()
    {
        Waits.waitForVisibility(driver,proceedCheckLogin).click();
    }
    public void payAsGuest() {
        Waits.waitForClickable(driver, guest).click();
        Waits.waitForVisibility(driver, guestEmail).sendKeys(RandomData.generateRandomEmail());
        driver.findElement(guestFirstName).sendKeys(testData.getJsonData("$.invalidUser.firstName"));
        driver.findElement(guestLastName).sendKeys(testData.getJsonData("$.invalidUser.lastName"));
        Waits.waitForClickable(driver, continueGuest).click();
    }
    public void ProceedPaymentAsGuest()
    {
        Waits.waitForVisibility(driver,proceedToCheckout).click();
    }

    public boolean isCheckoutLoginDisplayed()
    {
        return Waits.waitForVisibility(driver,signIn).isDisplayed();
    }
    public boolean isFinishButtonEnabled() {
        return driver.findElement(finishButton).isEnabled();
    }

    public boolean isOrderConfirmed() {
        return Waits.waitForVisibility(driver, orderConfirmation).isDisplayed();
    }

    public Boolean isUserLoggedIn() {
        return Waits.waitForVisibility(driver, isLoggedIn).isDisplayed();
    }

    public String getLoggedInMessage() {
        return Waits.waitForVisibility(driver, isLoggedIn).getText();
    }
}