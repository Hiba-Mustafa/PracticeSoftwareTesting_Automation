package pages;

import Base.BaseTest;
import CustomListeners.TestNGListeners;
import io.PaySky.pages.utiles.Waits;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

@Epic("E-commerce Flow")
@Feature("Login Page")
public class TestLoginPage extends BaseTest {
    private final By title = By.cssSelector("h1[data-test='page-title']");

    @Test(priority = 1, retryAnalyzer = TestNGListeners.class)
    @Story("User accesses the login page")
    @Description("verify that user can access login Page")
    @Severity(SeverityLevel.CRITICAL)
    public void testAccessToLoginPage() {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("Login page accessible");
        });
        homePage.clickSignIn();
        System.out.println(driver.getCurrentUrl());
        String expectedURL = "https://practicesoftwaretesting.com/auth/login";
        Waits.waitForValueToAppear(driver, "/auth/login");
        softAssert.assertTrue(loginPage.isLoggedIn(expectedURL));
        softAssert.assertAll();
    }


    @Test(priority = 2 , retryAnalyzer = TestNGListeners.class)
    @Story("User logs in with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a registered user can log in successfully with valid email and password")
    public void testLoginWithRegisteredCredentials() {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("Valid login");
        });
        loginPage.loginWithJsonUser(2, "email", "password");
        Waits.waitForValueToAppear(driver, "/account");
        String loginUrl = "/account";
        softAssert.assertTrue(loginPage.successfuLogin(loginUrl, title), "User logged in successfully");
        softAssert.assertAll();
    }

    @Test(priority = 3, retryAnalyzer = TestNGListeners.class)
    @Story("User logs out")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify a logged-in user can log out and is redirected to the login page")
    public void testLogOut() {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("Test log out");
        });
        logOutPage.logout();
        Waits.waitForValueToAppear(driver, "/auth/login");
        String currentUrl = "https://practicesoftwaretesting.com/auth/login";
        softAssert.assertTrue(logOutPage.isLoggedOut(currentUrl), "User logged out successfully");
        softAssert.assertAll();

        }

    @Step("Confirm redirect to login URL")
    public void loginStep() {
        System.out.println(driver.getCurrentUrl());
    }

    @Test(priority = 4)
    public void testInvalidLogin() {
        Allure.getLifecycle().updateTestCase(testResult ->
        {
            testResult.setName("Invalid login");
        });
        loginPage.eraseText();
        loginPage.loginWithInvalidCredentials();
        String expectedMsg = "Invalid email or password";
        String actualMsg = loginPage.getValidationMsg(expectedMsg);
        softAssert.assertEquals(actualMsg, expectedMsg, "User cannot login for missing password");
        softAssert.assertAll();
    }

}