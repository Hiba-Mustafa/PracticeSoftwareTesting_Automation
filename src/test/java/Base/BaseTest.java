package Base;

import io.PaySky.pages.*;

import io.PaySky.pages.utiles.ScreenShotUtils;
import io.PaySky.pages.utiles.Waits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;
    public static Registration register;
    public static HomePage homePage;
    public static LoginPage loginPage;
    public static LogOutPage logOutPage;
    public static SoftAssert softAssert;
    public static ProductPage productPage;
    public static CartPage cartPage;
    public static CheckOutPage checkOutPage;

    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
        loginPage = new LoginPage(driver);
        register = new Registration(driver);
        homePage = new HomePage(driver);
        logOutPage = new LogOutPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkOutPage = new CheckOutPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
    public void ensureLoggedOut() {
        driver.get("https://practicesoftwaretesting.com/");
        if (!homePage.isSignInVisible()) {
            logOutPage.logout();
        }
    }
}




