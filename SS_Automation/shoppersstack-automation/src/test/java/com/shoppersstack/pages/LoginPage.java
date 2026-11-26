package com.shoppersstack.pages;

import com.shoppersstack.utils.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) { super(driver); }

    public LoginPage openLogin() { click(Locators.HOME_LOGIN); visible(Locators.EMAIL); return this; }
    public LoginPage chooseMerchant() { click(Locators.MERCHANT_TAB); return this; }
    public LoginPage chooseAdmin() { click(Locators.ADMIN_TAB); return this; }

    public LoginPage login(String email, String password) {
        type(Locators.EMAIL, email);
        type(Locators.PASSWORD, password);
        click(Locators.SUBMIT_LOGIN);
        return this;
    }

    /** Clicks Login without typing anything (button may be disabled -> ignored). */
    public LoginPage submitEmpty() {
        try { click(Locators.SUBMIT_LOGIN); } catch (Exception ignored) { }
        return this;
    }

    /** Shopper logged in = account menu visible. */
    public boolean isShopperLoggedIn() { return isVisible(Locators.ACCOUNT, 12); }

    public boolean isStillOnLoginForm() { return isVisible(Locators.EMAIL, 5) && !isVisible(Locators.ACCOUNT, 2); }

    public boolean urlContains(String part) {
        try { return wait.until(ExpectedConditions.urlContains(part)); } catch (Exception e) { return false; }
    }

    public void logout() {
        click(Locators.ACCOUNT);
        click(Locators.LOGOUT);
    }

    public boolean isLoggedOut() { return isVisible(Locators.HOME_LOGIN, 10); }
}
