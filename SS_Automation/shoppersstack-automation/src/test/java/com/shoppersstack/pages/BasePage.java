package com.shoppersstack.pages;

import com.shoppersstack.utils.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    
    protected void jsClick(By locator) {
        WebElement e = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", e);
    }
    
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.WAIT_SECONDS));
    }

    protected WebElement visible(By l) { return wait.until(ExpectedConditions.visibilityOfElementLocated(l)); }

    protected void click(By l) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(l)).click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", visible(l));
        }
    }

    protected void type(By l, String value) {
        WebElement e = visible(l);
        e.clear();
        e.sendKeys(value);
    }

    public boolean isVisible(By l, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOfElementLocated(l));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isEnabled(By l) { return visible(l).isEnabled(); }
}
