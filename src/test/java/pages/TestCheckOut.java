package pages;

import Base.BaseTest;
import CustomListeners.TestNGListeners;
import io.PaySky.pages.utiles.JsonReader;
import io.PaySky.pages.utiles.Waits;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("E-commerce Flow")
@Feature("Checkout")
public class TestCheckOut extends BaseTest {

    private final By isLoggedIn = By.cssSelector("p[class='ng-star-inserted']");
    private final By confirmPayment = By.cssSelector("div[data-test='payment-success-message']");
    private static final String PRODUCT_NAME = "Hammer";
    private final By errorNotification = By.cssSelector("div[class='ng-star-inserted']");
    private static final String HOME_URL = "https://practicesoftwaretesting.com/";
    private final By notification = By.cssSelector("#toast-container .toast-success");
    private final String cartURL = "https://practicesoftwaretesting.com/checkout";
    private final By missingPassword = By.id("password-error");
    private final String handToolCat = "Hand Tools";
    private final By userName =  By.id("menu");
    private final By billingAdd = By.xpath("//h3[text()='Billing Address']");
    private final By countryDropdown = By.id("country");
    private final By postalCodeField = By.id("postal_code");
    private final By houseNumberField = By.id("house_number");
    private final JsonReader testData = new JsonReader("users");
    private final By submitLogin = By.cssSelector("input[data-test='login-submit']");
    private final By continueAsGuest = By.cssSelector("p[class='ng-star-inserted']");

    @Test(priority = 1 , retryAnalyzer = TestNGListeners.class)
    @Story("Registered user completes checkout with cash on delivery")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a new user can register, log in, add a product, and complete checkout paying cash on delivery")
    public void testRegisterPurchaseCashOnDelivery() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Valid E2E purchase");
        });
        ensureLoggedOut();
        homePage.clickSignIn();
        register.clickOnRegistrationLink();
        register.fillFormWithFixedData();
        register.submitRegisterButton();
        Waits.waitForValueToAppear(driver, "/auth/login");
        loginPage.loginWith();
        String loginUrl = "/account";
        Waits.waitForValueToAppear(driver,loginUrl);
        homePage.chooseFromCategory(handToolCat);
        productPage.clickOnProductName(PRODUCT_NAME);
        productPage.addToCart();
        Waits.waitForElementToDisappear(driver, notification);
        try {
            productPage.goToCart();
        } catch (ElementClickInterceptedException e) {
            Waits.waitForElementToDisappear(driver, notification);
            driver.get(cartURL);
        }
        cartPage.proceedToCheckout();
        checkOutPage.confirmAlreadyLoggedIn();
        checkOutPage.fillAddress();
        checkOutPage.proceedToPayment();
        checkOutPage.payWithCashOnDelivery();
        String expectedMsg = "Payment was successful";
        WebElement actualMsg = Waits.waitForVisibility(driver,confirmPayment);
        softAssert.assertEquals(actualMsg.getText().trim(),expectedMsg,"Order is confirmed");
        softAssert.assertTrue(
                checkOutPage.isOrderConfirmed(),
                "Expected order confirmation after cash-on-delivery checkout");
        softAssert.assertAll();
    }


    @Test(priority = 2, retryAnalyzer = TestNGListeners.class)
    @Story("Guest checkout with valid credit card, logging in mid-checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a guest can add a product, check out as guest, log in during checkout, and pay successfully with a valid credit card")
    public void testPurchaseAsGuestPayWithValidCreditCard() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Make a purchase as a guest user and pay with a valid credit card.");
        });
        logOutPage.logout();
        driver.get(HOME_URL);
        productPage.clickOnProductName(PRODUCT_NAME);
        productPage.addToCart();
        Waits.waitForElementToDisappear(driver, notification);
        try {
            productPage.goToCart();
        } catch (ElementClickInterceptedException e) {
            driver.get(cartURL);
        }
        cartPage.proceedToCheckout();
        checkOutPage.payAsGuest();
        WebElement guest = Waits.waitForVisibility(driver,continueAsGuest);
        String expectedMessage = "Continuing as guest:";
        Assert.assertTrue(guest.getText().contains(expectedMessage),"Pay as guest user");
        checkOutPage.ProceedPaymentAsGuest();
        Waits.waitForVisibility(driver,billingAdd);
        try {
            checkOutPage.fillAddress();
            checkOutPage.waitForAutoFilledFields();
            checkOutPage.proceedToPayment();
        }
        catch (ElementClickInterceptedException e)
        {
            new Select(Waits.waitForVisibility(driver, countryDropdown)).selectByVisibleText(testData.getJsonData("$.registration.country"));
            driver.findElement(postalCodeField).sendKeys(testData.getJsonData("$.registration.postalCode"));
            driver.findElement(houseNumberField).sendKeys(testData.getJsonData("$.registration.houseNumber"));
            checkOutPage.waitForAutoFilledFields();
            checkOutPage.proceedToPayment();
        }
        checkOutPage.payWithValidCreditCard();
        String expectedMsg = "Payment was successful";
        WebElement actualMsg = Waits.waitForVisibility(driver,confirmPayment);
        softAssert.assertEquals(actualMsg.getText().trim(),expectedMsg,"Order is confirmed");
        softAssert.assertTrue(
                checkOutPage.isOrderConfirmed(),
                "Expected order confirmation after cash-on-delivery checkout");
        softAssert.assertAll();
    }


}


