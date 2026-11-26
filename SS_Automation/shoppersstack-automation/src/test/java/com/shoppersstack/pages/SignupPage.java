package com.shoppersstack.pages;

import com.shoppersstack.utils.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {
    public SignupPage(WebDriver driver) { super(driver); }

    public SignupPage open() {
        click(Locators.HOME_LOGIN);
        click(Locators.CREATE_ACCOUNT);
        visible(Locators.FIRST_NAME);
        return this;
    }

    public SignupPage fill(String fn, String ln, String phone, String email, String pw, String cpw, boolean terms) {
        type(Locators.FIRST_NAME, fn);
        type(Locators.LAST_NAME, ln);
        jsClick(By.id("Male"));
        type(Locators.PHONE, phone);
        type(Locators.SIGNUP_EMAIL, email);
        type(Locators.SIGNUP_PASSWORD, pw);
        type(Locators.CONFIRM_PASSWORD, cpw);
        if (terms) jsClick(By.xpath("//input[@type='checkbox']"));
        return this;
    }

    public boolean isRegisterEnabled() { return isEnabled(Locators.REGISTER); }
    public void clickRegister() { click(Locators.REGISTER); }
    public boolean isOnSignupForm() { return isVisible(Locators.FIRST_NAME, 4); }
}