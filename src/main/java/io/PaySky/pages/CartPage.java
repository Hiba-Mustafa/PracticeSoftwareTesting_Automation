package io.PaySky.pages;

import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;

    private final By increaseBtn = By.id("btn-increase-quantity");
    private final By decreaseBtn = By.cssSelector("btn-decrease-quantity");
    private final By proceedCheckoutBtn = By.cssSelector("button[data-test='proceed-1']");
    private final By productQuantity = By.cssSelector("input[data-test='product-quantity']");
    private final By cartTotal = By.cssSelector("[data-test='cart-total']");
    private final By continueShopping = By.cssSelector("button[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void increaseQuantity()
    {
        Waits.waitForClickable(driver, increaseBtn).click();
    }

    public void decreaseQuantity()
    {
        Waits.waitForClickable(driver, decreaseBtn).click();
    }

    public By removeItem(String item)
    {
        return By.xpath("//tr[contains(.,'"+ item+"')]//a[contains(@class, 'btn-danger')]");
    }

    public void removeProduct(String itemName)
    {

        Waits.waitForClickable(driver, removeItem(itemName)).click();
    }


    public String getCartTotal()
    {
        return Waits.waitForVisibility(driver, cartTotal).getText();
    }

    public void continueShopping()
    {
        Waits.waitForVisibility(driver,continueShopping).click();
    }

    public String getNotificationMsg(By locator)
    {
        return driver.findElement(locator).getText();
    }

    public void proceedToCheckout()
    {
        Waits.waitForClickable(driver, proceedCheckoutBtn).click();
    }
}