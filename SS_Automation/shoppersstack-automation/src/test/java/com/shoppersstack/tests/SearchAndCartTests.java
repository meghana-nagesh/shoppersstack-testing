package com.shoppersstack.tests;

import com.shoppersstack.base.BaseTest;
import com.shoppersstack.pages.*;
import com.shoppersstack.utils.Config;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchAndCartTests extends BaseTest {

    private HomePage loggedInHome() {
        LoginPage lp = new LoginPage(driver).openLogin().login(Config.SHOPPER_EMAIL, Config.SHOPPER_PASSWORD);
        Assert.assertTrue(lp.isShopperLoggedIn(), "Pre-condition failed: login");
        return new HomePage(driver);
    }

    /** Logs in, empties the cart, and goes back to the home page. */
    private HomePage loggedInHomeWithEmptyCart() {
        HomePage hp = loggedInHome();
        hp.openCart();
        new CartPage(driver).clearCart();
        driver.get("https://www.shoppersstack.com/");
        return new HomePage(driver);
    }

    @Test(description = "SS_TC_008 | Search for an existing product", groups = {"smoke", "functional", "regression"})
    public void searchExistingProduct() {
        HomePage hp = loggedInHome();
        hp.search("maybelline");
        Assert.assertTrue(hp.textShown("maybelline", 10), "Matching product not displayed");
    }

    @Test(description = "SS_TC_009 | Search for a non-existing product", groups = {"functional", "negative"})
    public void searchNonExistingProduct() {
        HomePage hp = loggedInHome();
        hp.search("HYIYT6536");
        Assert.assertFalse(hp.textShown("hyiyt6536 lipstick", 3), "No product should match a junk term");
        Assert.assertTrue(hp.searchBoxVisible(), "Search page should stay usable");
    }

    @Test(description = "SS_TC_010 | Add a product to the cart", groups = {"smoke", "functional", "regression"})
    public void addToCart() {
        HomePage hp = loggedInHomeWithEmptyCart();
        int before = hp.cartCount();
        hp.addFirstProductToCart();
        Assert.assertTrue(hp.addedButtonShown() || hp.cartCount() == before + 1, "Product was not added to cart");
    }

    @Test(description = "SS_TC_011 | Added product is shown in the cart", groups = {"integration", "regression"})
    public void addedProductShownInCart() {
        HomePage hp = loggedInHomeWithEmptyCart();
        hp.addFirstProductToCart();
        hp.openCart();
        Assert.assertTrue(new CartPage(driver).hasItems(), "Cart page does not list the added product, url=" + driver.getCurrentUrl());
    }

    // SKIPPED - SS_TC_012: the cart page has no visible + / - quantity buttons on the live site.
    // Re-enable once the quantity control locator is known.
    /*
    @Test(description = "SS_TC_012 | Cart quantity increases and decreases", groups = {"integration", "regression"})
    public void cartQuantityChange() {
        HomePage hp = loggedInHomeWithEmptyCart();
        hp.addFirstProductToCart();
        hp.openCart();
        CartPage cp = new CartPage(driver);
        Assert.assertTrue(cp.hasItems());
        String q1 = cp.quantity();
        cp.increase();
        Assert.assertNotEquals(cp.quantity(), q1, "Quantity did not change after +");
        cp.decrease();
        Assert.assertEquals(cp.quantity(), q1, "Quantity did not return after -");
    }
    */

    // SKIPPED - SS_TC_013: Buy Now -> address -> Cash on Delivery flow locators are unverified,
    // and this test places a real order. Re-enable once the checkout page is inspected.
    /*
    @Test(description = "SS_TC_013 | Place an order with saved address and Cash on Delivery", groups = {"system", "regression"})
    public void placeOrderCod() {
        HomePage hp = loggedInHomeWithEmptyCart();
        hp.addFirstProductToCart();
        hp.openCart();
        CartPage cp = new CartPage(driver);
        Assert.assertTrue(cp.hasItems());
        cp.buyNowWithFirstAddressCod();
        Assert.assertTrue(hp.textShown("order", 10), "Order confirmation not displayed");
    }
    */

    @Test(description = "SS_TC_015 | Wishlist page opens from account menu", groups = {"integration"})
    public void openWishlist() {
        HomePage hp = loggedInHome();
        hp.openAccountMenu(com.shoppersstack.utils.Locators.WISHLIST);
        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("wish") || hp.textShown("wish", 8));
    }

    @Test(description = "SS_TC_018-020 | My Addresses page opens from My Profile", groups = {"system"})
    public void openMyAddresses() {
        HomePage hp = loggedInHome();
        hp.openAccountMenu(com.shoppersstack.utils.Locators.MY_PROFILE);
        hp.openAccountMenu(com.shoppersstack.utils.Locators.MY_ADDRESSES);
        Assert.assertTrue(driver.getCurrentUrl().contains("my-addresses"), "My Addresses page not displayed");
    }

    // ---------- Security ----------
    @Test(description = "SEC_001 | XSS payload in search must not execute", groups = {"security", "negative"})
    public void xssInSearch() {
        HomePage hp = loggedInHome();
        hp.search("<script>alert('xss')</script>");
        boolean alertShown;
        try { driver.switchTo().alert(); alertShown = true; } catch (NoAlertPresentException e) { alertShown = false; }
        Assert.assertFalse(alertShown, "XSS alert executed!");
    }

    // ---------- Boundary ----------
    @Test(description = "BND_001 | Very long search text is handled gracefully", groups = {"boundary", "negative"})
    public void longSearchText() {
        HomePage hp = loggedInHome();
        hp.search("a".repeat(300));
        Assert.assertTrue(hp.searchBoxVisible(), "Application broke on very long search text");
    }
}