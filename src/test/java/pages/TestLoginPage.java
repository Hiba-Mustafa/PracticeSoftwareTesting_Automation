package pages;

import Base.BaseTest;
import io.PaySky.pages.utiles.Waits;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

@Epic("E-commerce Flow")
@Feature("Login Page")
public class TestLoginPage extends BaseTest {
    private final By title = By.cssSelector("h1[data-test='page-title']");

    @Test(priority = 1)
    @Description("verify that user can access login Page")
    @Tag("Validation")
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


    @Test(priority = 2)
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

    @Test(priority = 3)
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