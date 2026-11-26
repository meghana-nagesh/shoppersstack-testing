package com.shoppersstack.tests;

import com.shoppersstack.base.BaseTest;
import com.shoppersstack.pages.HomePage;
import com.shoppersstack.pages.LoginPage;
import com.shoppersstack.utils.Config;
import com.shoppersstack.utils.Locators;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NonFunctionalTests extends BaseTest {

    @Test(description = "PERF_001 | Home page loads within 10 seconds", groups = {"performance", "smoke"})
    public void homePageLoadTime() {
        long start = System.currentTimeMillis();
        driver.get(Config.BASE_URL);
        long secs = (System.currentTimeMillis() - start) / 1000;
        Assert.assertTrue(secs <= 10, "Home page took " + secs + "s to load");
    }

    @Test(description = "UI_001 | Landing page title and Login button are displayed", groups = {"ui", "smoke"})
    public void landingPageElements() {
        Assert.assertFalse(driver.getTitle().isBlank(), "Page title is empty");
        Assert.assertTrue(new HomePage(driver).isVisible(Locators.HOME_LOGIN, 10), "Login button not visible");
    }

    @Test(description = "COMPAT_001 | Landing page renders on a mobile viewport", groups = {"compatibility", "ui"})
    public void mobileViewport() {
        driver.manage().window().setSize(new Dimension(390, 844));
        driver.navigate().refresh();
        Assert.assertFalse(driver.getTitle().isBlank(), "Page did not render on mobile viewport");
    }

    @Test(description = "SEC_002 | SQL-injection style login input is rejected", groups = {"security", "negative"})
    public void sqlInjectionLogin() {
        LoginPage lp = new LoginPage(driver).openLogin().login("' OR '1'='1", "' OR '1'='1");
        Assert.assertTrue(lp.isStillOnLoginForm(), "Injection input must not log the user in");
    }

    @Test(description = "SEC_003 | Protected page is not reachable without login", groups = {"security", "functional"})
    public void protectedPageWithoutLogin() {
        driver.get(Config.BASE_URL + "cart");
        Assert.assertFalse(new LoginPage(driver).isShopperLoggedIn(), "Cart should not expose a logged-in session");
    }
}
