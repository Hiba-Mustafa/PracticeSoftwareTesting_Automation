package io.PaySky.pages;

import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private final By addToCartButton = By.id("btn-add-to-cart");
    private final By categories = By.cssSelector("button[data-test='nav-categories']");
    private final By increaseBtn = By.id("btn-increase-quantity");
    private final By cartIcon = By.cssSelector("a[data-test='nav-cart']");
    private final WebDriver driver;


    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnProductName(String product) {
        Waits.waitForVisibility(driver, By.xpath("//h5[normalize-space()='" + product + "']")).click();
    }

    public void addToCart() {
        Waits.waitForVisibility(driver, addToCartButton).click();
    }

    public void increaseProductQuantity() {
        Waits.waitForClickable(driver, increaseBtn).click();
    }

    public By categories(String category)
    {
        return By.xpath("//ul[@aria-label='nav-categories']//a[normalize-space()='" + category + "']");
    }

    public void chooseFromCategory(String category) {
        Waits.waitForVisibility(driver, categories).click();
        driver.findElement(categories(category)).click();
    }

    public void goToCart() {
        Waits.waitForVisibility(driver,cartIcon).click();
    }
    public String getNotificationMsg(By locator)
    {
        return driver.findElement(locator).getText();
    }
}