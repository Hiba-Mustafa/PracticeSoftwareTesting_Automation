package pages;

import Base.BaseTest;
import CustomListeners.TestNGListeners;
import io.PaySky.pages.utiles.Waits;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
@Epic("E-commerce Flow")
@Feature("Registration Feature")
public class RegistrationTest extends BaseTest {

    private final By successLogin = By.cssSelector("h1[data-test='page-title']");
    SoftAssert softAssert = new SoftAssert();

    @org.testng.annotations.Test(priority = 1, retryAnalyzer = TestNGListeners.class)
    @Story("New user registers an account")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a new customer can register and is redirected to the login page")
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

    @Test (priority = 2, retryAnalyzer = TestNGListeners.class)
    @Story("User logs in after registering")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a newly registered customer can log in with their new credentials")
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

    @Test (priority = 3, retryAnalyzer = TestNGListeners.class)
    @Story("Registration fails with missing required data")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify registering with a missing last name shows the correct validation message")
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