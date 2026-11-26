package com.shoppersstack.pages;

import com.shoppersstack.utils.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) { super(driver); }
    public boolean addedButtonShown() {
        return isVisible(By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'added')]"), 8);
    }
    public void search(String term) {
        type(Locators.SEARCH_BOX, term);
        try { click(Locators.SEARCH_ICON); }
        catch (Exception e) { visible(Locators.SEARCH_BOX).sendKeys(Keys.ENTER); }
    }

    public boolean textShown(String t, int seconds) { return isVisible(Locators.text(t), seconds); }
    public boolean searchBoxVisible() { return isVisible(Locators.SEARCH_BOX, 8); }

    public int cartCount() {
        try {
            String s = driver.findElement(Locators.CART_BADGE).getText().trim();
            return s.isEmpty() ? 0 : Integer.parseInt(s);
        } catch (Exception e) { return 0; }
    }

    public void addFirstProductToCart() {
        jsClick(By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to cart')]"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'added')]")));
    }
    public void openCart() { click(Locators.CART_ICON); }
    public void openAccountMenu(By item) { click(Locators.ACCOUNT); click(item); }
}
