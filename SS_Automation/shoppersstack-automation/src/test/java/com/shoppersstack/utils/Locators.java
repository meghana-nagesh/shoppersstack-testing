package com.shoppersstack.utils;

import org.openqa.selenium.By;

/**
 * ALL locators live here. If a test fails with NoSuchElement/Timeout,
 * inspect the element in DevTools and fix ONLY the matching line below.
 */
public final class Locators {
    private Locators() {}

    private static final String FROM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String TO = "abcdefghijklmnopqrstuvwxyz";

    /** Case-insensitive button containing the text. */
    public static By btn(String t) {
        return By.xpath("//button[contains(translate(normalize-space(.),'" + FROM + "','" + TO + "'),'" + t.toLowerCase() + "')]");
    }

    /** Case-insensitive element whose own text contains the text. */
    public static By text(String t) {
        return By.xpath("//*[text()[contains(translate(normalize-space(.),'" + FROM + "','" + TO + "'),'" + t.toLowerCase() + "')]]");
    }

    // ---- Login ----
    public static final By HOME_LOGIN = By.xpath("//button[@id='loginBtn'] | //button[normalize-space()='Login']");
    public static final By EMAIL = By.cssSelector("#Email, input[name='email'], input[type='email']");
    public static final By PASSWORD = By.cssSelector("#Password, input[name='password'], input[type='password']");
    public static final By SUBMIT_LOGIN = By.xpath("//button[@id='Login'] | //form//button[normalize-space()='Login']");
    public static final By MERCHANT_TAB = btn("merchant");
    public static final By ADMIN_TAB = btn("admin");

    // ---- Signup ----
    public static final By CREATE_ACCOUNT = btn("create account");
    public static final By FIRST_NAME = By.cssSelector("[id='First Name'], input[name='firstName'], input[placeholder*='First']");
    public static final By LAST_NAME = By.cssSelector("[id='Last Name'], input[name='lastName'], input[placeholder*='Last']");
    public static final By PHONE = By.cssSelector("[id='Phone Number'], input[name='phone'], input[placeholder*='Phone']");
    public static final By SIGNUP_EMAIL = By.cssSelector("[id='Email Address'], input[name='email'], input[type='email']");
    public static final By SIGNUP_PASSWORD = By.cssSelector("[id='Password'], input[name='password']");
    public static final By CONFIRM_PASSWORD = By.cssSelector("[id='Confirm Password'], input[name='confirmPassword']");
    public static final By GENDER_MALE = By.cssSelector("#Male, input[value='Male']");
    public static final By TERMS = By.cssSelector("input[type='checkbox']");
    public static final By REGISTER = By.xpath("//button[@id='Register'] | //button[normalize-space()='Register']");

    // ---- Home / search / cart ----
    public static final By SEARCH_BOX = By.cssSelector("input#search, input[placeholder*='earch']");
    public static final By SEARCH_ICON = By.cssSelector("#searchBtn, button[aria-label*='earch'], [data-testid='SearchIcon']");
    public static final By ADD_TO_CART = btn("add to cart");
    public static final By CART_ICON = By.cssSelector("#cart, a[href*='cart'], [data-testid='ShoppingCartIcon'], [aria-label*='art']");
    public static final By CART_BADGE = By.cssSelector(".MuiBadge-badge");
    public static final By PLUS = By.xpath("//*[self::button or self::span or self::div or self::a][normalize-space(text())='+'] | //button[.//*[@data-testid='AddIcon']] | //button[contains(@aria-label,'ncrease')]");
    public static final By MINUS = By.xpath("//*[self::button or self::span or self::div or self::a][normalize-space(text())='-' or normalize-space(text())='\u2212'] | //button[.//*[@data-testid='RemoveIcon']] | //button[contains(@aria-label,'ecrease')]");
    public static final By QUANTITY = By.xpath("(//*[self::button or self::span or self::div or self::a][normalize-space(text())='+']/preceding-sibling::*[1] | //*[self::button or self::span or self::div or self::a][normalize-space(text())='+']/preceding::input[1])[1]");

    // ---- Account menu ----
    public static final By ACCOUNT = By.cssSelector("#account, [aria-label*='ccount'], [data-testid='AccountCircleIcon']");
    public static final By LOGOUT = text("logout");
    public static final By MY_PROFILE = text("my profile");
    public static final By MY_ADDRESSES = text("my addresses");
    public static final By WISHLIST = text("wish list");

    // ---- Order ----
    public static final By BUY_NOW = btn("buy now");
    public static final By PROCEED = btn("proceed");
    public static final By ADDRESS_RADIO = By.cssSelector("input[type='radio']");
    public static final By COD = text("cash on delivery");
}