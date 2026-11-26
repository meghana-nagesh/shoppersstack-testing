package com.shoppersstack.tests;

import com.shoppersstack.base.BaseTest;
import com.shoppersstack.pages.LoginPage;
import com.shoppersstack.utils.Config;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "SS_TC_001 | Verify login using valid credentials", groups = {"smoke", "functional", "regression"})
    public void validLogin() {
        LoginPage lp = new LoginPage(driver).openLogin().login(Config.SHOPPER_EMAIL, Config.SHOPPER_PASSWORD);
        Assert.assertTrue(lp.isShopperLoggedIn(), "Home page / account menu not displayed after valid login");
    }

    @Test(description = "SS_TC_002a | Login with valid email and invalid password", groups = {"functional", "negative", "regression"})
    public void validEmailInvalidPassword() {
        LoginPage lp = new LoginPage(driver).openLogin().login(Config.SHOPPER_EMAIL, "Gu@123");
        Assert.assertTrue(lp.isStillOnLoginForm(), "User should not be logged in");
    }

    @Test(description = "SS_TC_002b | Login with invalid email and valid password", groups = {"functional", "negative"})
    public void invalidEmailValidPassword() {
        LoginPage lp = new LoginPage(driver).openLogin().login("leie@gmail.com", Config.SHOPPER_PASSWORD);
        Assert.assertTrue(lp.isStillOnLoginForm(), "User should not be logged in");
    }

    @Test(description = "SS_TC_002c | Login with invalid email and invalid password", groups = {"functional", "negative"})
    public void invalidEmailInvalidPassword() {
        LoginPage lp = new LoginPage(driver).openLogin().login("lei@gmail.com", "Gbi@1");
        Assert.assertTrue(lp.isStillOnLoginForm(), "User should not be logged in");
    }

    @Test(description = "SS_TC_002d | Login with both fields empty", groups = {"functional", "negative", "ui"})
    public void emptyLogin() {
        LoginPage lp = new LoginPage(driver).openLogin().submitEmpty();
        Assert.assertTrue(lp.isStillOnLoginForm(), "Login must not succeed with empty fields");
    }

    @Test(description = "SS_TC_LOGOUT | Shopper can log out after login", groups = {"smoke", "system"})
    public void logout() {
        LoginPage lp = new LoginPage(driver).openLogin().login(Config.SHOPPER_EMAIL, Config.SHOPPER_PASSWORD);
        Assert.assertTrue(lp.isShopperLoggedIn());
        lp.logout();
        Assert.assertTrue(lp.isLoggedOut(), "Login button should be visible after logout");
    }

    @Test(description = "SS.M_TC_028 | Merchant login with valid credentials", groups = {"functional", "regression"})
    public void merchantLogin() {
        LoginPage lp = new LoginPage(driver).openLogin();
        driver.findElement(By.id("vertical-tab-1")).click();
        lp.login(Config.MERCHANT_EMAIL, Config.MERCHANT_PASSWORD);
        Assert.assertFalse(lp.isStillOnLoginForm(), "Merchant login did not succeed, url=" + driver.getCurrentUrl());    }

    @Test(description = "SS.A_TC_029 | Admin login with valid credentials", groups = {"functional", "regression"})
    public void adminLogin() {
        LoginPage lp = new LoginPage(driver).openLogin();
        driver.findElement(By.id("vertical-tab-2")).click();
        lp.login(Config.ADMIN_EMAIL, Config.ADMIN_PASSWORD);
        Assert.assertFalse(lp.isStillOnLoginForm(), "Admin login did not succeed, url=" + driver.getCurrentUrl());}
}