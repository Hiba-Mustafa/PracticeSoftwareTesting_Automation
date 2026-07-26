package pages;

import Base.BaseTest;
import io.PaySky.pages.utiles.Waits;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class TestAddToCart extends BaseTest {
    private final By notification = By.cssSelector("#toast-container .toast-success");
    private final By productTitle = By.cssSelector("h1[data-test='product-name']");
    private final String productName = "Pliers";
    private final String productName1 = "Combination Pliers";
    private final By item = By.cssSelector("th[class='col-md-5']");
    private final String cartURL = "https://practicesoftwaretesting.com/checkout";


    @Test(priority = 1)
    public void testAddProductToCart() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Product added to cart");
        });
        productPage.clickOnProductName(productName1);
        Waits.waitForVisibility(driver, productTitle);
        String actualTitle = driver.findElement(productTitle).getText();
        softAssert.assertTrue(actualTitle.contains(productName1));
        productPage.addToCart();

        String notificationMsg = "Product added to shopping cart.";
        WebElement toast = Waits.waitForVisibility(driver, notification);
        String actualMsg = toast.getText();
        softAssert.assertEquals(actualMsg, notificationMsg, "Items is added to cart");

        try {
            productPage.goToCart();
        } catch (ElementClickInterceptedException e) {
            Waits.waitForElementToDisappear(driver, notification);
            driver.get(cartURL);
        }

        Waits.waitForVisibility(driver, item);
        String actualURL = driver.getCurrentUrl();
        String expectedURL = "https://practicesoftwaretesting.com/checkout";
        softAssert.assertEquals(actualURL, expectedURL);
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void testRemoveProductFromCart() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Remove product from cart");
        });
        cartPage.continueShopping();
        productPage.clickOnProductName(productName);
        productPage.increaseProductQuantity();
        productPage.addToCart();
        try {
            productPage.goToCart();
        } catch (ElementClickInterceptedException e) {
            Waits.waitForElementToDisappear(driver, notification);
            driver.get(cartURL);
        }
        Waits.waitForVisibility(driver, item);
        cartPage.removeProduct(productName1);
        String deleteMsg = "Product deleted.";
        WebElement toast = Waits.waitForVisibility(driver, notification);
        softAssert.assertEquals(toast.getText(), deleteMsg, productName1 + " is Removed from cart");
        System.out.println(cartPage.getCartTotal());
        softAssert.assertAll();

    }
}