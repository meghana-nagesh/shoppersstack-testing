package com.shoppersstack.pages;

import com.shoppersstack.utils.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) { super(driver); }

    public boolean hasItems() { return isVisible(Locators.text("remove from cart"), 10); }

    /** Removes every product from the cart so the home page shows Add to Cart buttons again. */
    public void clearCart() {
        for (int i = 0; i < 30; i++) {
            if (driver.findElements(Locators.text("remove from cart")).isEmpty()) break;
            jsClick(Locators.text("remove from cart"));
            try { Thread.sleep(700); } catch (InterruptedException ignored) {}
        }
    }

    public String quantity() {
        WebElement e = visible(Locators.QUANTITY);
        String v = e.getText().trim();
        return v.isEmpty() ? e.getAttribute("value") : v;
    }

    public void increase() { click(Locators.PLUS); }
    public void decrease() { click(Locators.MINUS); }

    public void buyNowWithFirstAddressCod() {
        click(Locators.BUY_NOW);
        click(Locators.ADDRESS_RADIO);
        click(Locators.PROCEED);
        click(Locators.COD);
        click(Locators.PROCEED);
    }
}