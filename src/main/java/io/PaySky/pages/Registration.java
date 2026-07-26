package io.PaySky.pages;

import io.PaySky.pages.utiles.JsonReader;
import io.PaySky.pages.utiles.RandomData;
import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static io.PaySky.pages.utiles.Waits.waitForVisibility;


public class Registration {

    private final By firstNameField = By.id("first_name");
    private final By lastNameField = By.id("last_name");
    private final By dobField = By.id("dob");
    private final By countryDropdown = By.id("country");
    private final By postalCodeField = By.id("postal_code");
    private final By houseNumberField = By.id("house_number");
    private final By street = By.id("street");
    private final By city = By.id("city");
    private final By state = By.id("state");
    private final By phoneField = By.id("phone");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By registerButton = By.cssSelector("button[data-test='register-submit']");
    private final By registerLink = By.cssSelector("a[href='/auth/register']");
    private final JsonReader testData;
    private WebDriver driver;

    public Registration(WebDriver driver) {
        this.driver = driver;
        this.testData = new JsonReader("users"); // reads test-data/data.json
    }


    public void clickOnRegistrationLink() {
        waitForVisibility(driver, registerLink).click();
    }

    public void fillFormWithFixedData() {
        Waits.waitForVisibility(driver, firstNameField).sendKeys(testData.getJsonData("$.registration.firstName"));
        driver.findElement(lastNameField).sendKeys(testData.getJsonData("$.registration.lastName"));
        driver.findElement(dobField).sendKeys(testData.getJsonData("$.registration.dob"));

        Select country = new Select(driver.findElement(countryDropdown));
        country.selectByVisibleText(testData.getJsonData("$.registration.country"));
        ;
        driver.findElement(postalCodeField).sendKeys(testData.getJsonData("$.registration.postalCode")); // fixed, valid for Germany
        driver.findElement(houseNumberField).sendKeys(testData.getJsonData("$.registration.houseNumber"));

        driver.findElement(phoneField).sendKeys(testData.getJsonData("$.registration.phone"));
        driver.findElement(emailField).sendKeys(RandomData.generateRandomEmail());
        driver.findElement(passwordField).sendKeys(RandomData.generateRandomPassword());
    }

    public void submitRegisterButton() {
        waitForAutoFilledFields();
        Waits.waitForClickable(driver, registerButton).click();
    }


    public void registerWithMissingData() {
        Waits.waitForVisibility(driver, firstNameField).sendKeys(testData.getJsonData("$.registration.firstName"));
        driver.findElement(dobField).sendKeys(testData.getJsonData("$.registration.dob"));

        Select country = new Select(driver.findElement(countryDropdown));
        country.selectByVisibleText(testData.getJsonData("$.registration.country"));
        ;
        driver.findElement(postalCodeField).sendKeys(testData.getJsonData("$.registration.postalCode")); // fixed, valid for Germany
        driver.findElement(houseNumberField).sendKeys(testData.getJsonData("$.registration.houseNumber"));

        driver.findElement(phoneField).sendKeys(testData.getJsonData("$.registration.phone"));
        driver.findElement(emailField).sendKeys(RandomData.generateRandomEmail());
        driver.findElement(passwordField).sendKeys(RandomData.generateRandomPassword());
    }

    public By validationMessage(String message) {
        return By.xpath("//div[normalize-space()='" + message + "']");
    }

    public String getValidationMsg(String message) {
        return Waits.waitForVisibility(driver,
                        validationMessage(message))
                .getText();
    }

    public void waitForAutoFilledFields() {
        Waits.waitFor(driver, d -> !d.findElement(street).getAttribute("value").isEmpty());
    }

    public WebElement getStreerValueElement() {
        return driver.findElement(street);
    }
}
