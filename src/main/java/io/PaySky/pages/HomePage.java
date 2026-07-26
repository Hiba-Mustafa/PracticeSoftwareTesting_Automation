package io.PaySky.pages;

import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static io.PaySky.pages.utiles.Waits.waitForClickable;
import static io.PaySky.pages.utiles.Waits.waitForVisibility;

public class HomePage {
    private WebDriver driver;
    private final By homeButton = By.cssSelector("a[data-test='nav-home']");
    private final By navigateToLogin = By.cssSelector("a[data-test='nav-sign-in']");
    private final By categories = By.cssSelector("[data-test='nav-categories']");
    private final By cartIcon= By.cssSelector("a[data-test='nav-cart']");
    private final By cartCount= By.cssSelector("[data-test='cart-quantity']");



    public HomePage (WebDriver driver)
    {
        this.driver=driver;
    }


    public void clickSignIn()
    {
        Waits.waitForClickable(driver, navigateToLogin).click();
    }

    public void goToCart() {
        Waits.waitForClickable(driver, cartIcon).click();
    }

    public void goToHomePage() {
        waitForVisibility(driver,homeButton).click();
    }

    public String getCartCount() {
        return Waits.waitForVisibility(driver, cartCount).getText();
    }
    public By categories(String category)
    {
        return By.xpath("//ul[@aria-label='nav-categories']//a[normalize-space()='" + category + "']");
    }

    public void chooseFromCategory(String category) {
        Waits.waitForVisibility(driver, categories).click();
        Waits.waitForVisibility(driver, categories(category)).click();
    }


    public boolean isSignInVisible() {
        return Waits.waitForVisibility(driver,navigateToLogin).isDisplayed();
    }
}
