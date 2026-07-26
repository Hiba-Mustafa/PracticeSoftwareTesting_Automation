package pages;

import Base.BaseTest;
import io.PaySky.pages.utiles.Waits;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
@Epic("E-commerce Flow")
@Feature("Registration Page")
public class RegistrationTest extends BaseTest {

    private final By successLogin = By.cssSelector("h1[data-test='page-title']");
    SoftAssert softAssert = new SoftAssert();

    @org.testng.annotations.Test(priority = 1)

    public void testRegisterNewCustomer() {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("New User Registration");
        });
        homePage.clickSignIn();
        register.clickOnRegistrationLink();
        register.fillFormWithFixedData();
        register.submitRegisterButton();
        Waits.waitForValueToAppear(driver,"/auth/login");
        String url = "https://practicesoftwaretesting.com/auth/login";
        softAssert.assertTrue(driver.getCurrentUrl().equals(url));
        softAssert.assertAll();
    }

    @Test (priority = 2)
    public void testLoginWithRegisteredCredentials() {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("login with registered credentials");
        });
        loginPage.loginWith();
        String loginUrl = "/account";
        Waits.waitForValueToAppear(driver,loginUrl);
        Waits.waitForVisibility(driver,successLogin);
        softAssert.assertTrue(loginPage.isLoggedIn(loginUrl), "Successful login");
        softAssert.assertAll();
    }

    @Test (priority = 3)
    public void testInvalidRegistration()
    {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("Invalid registration");
        });
        logOutPage.logout();
        homePage.clickSignIn();
        register.clickOnRegistrationLink();
        register.registerWithMissingData();
        register.submitRegisterButton();
        String expectedMsg = "Last name is required";
        String actualMsg = register.getValidationMsg(expectedMsg);
        softAssert.assertEquals(actualMsg,expectedMsg);
        softAssert.assertAll();
    }
}