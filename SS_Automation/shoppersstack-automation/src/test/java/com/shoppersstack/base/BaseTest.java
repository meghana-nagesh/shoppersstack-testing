package com.shoppersstack.base;

import com.shoppersstack.utils.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;

    public WebDriver getDriver() { return driver; }

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browserParam) {
        String sys = System.getProperty("browser");
        String browser = (sys != null && !sys.isBlank() && !sys.startsWith("${")) ? sys : browserParam;
        String h = System.getProperty("headless");
        boolean headless = h != null && h.equalsIgnoreCase("true");

        switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions o = new FirefoxOptions();
                if (headless) o.addArguments("-headless");
                driver = new FirefoxDriver(o);
            }
            case "edge" -> driver = new EdgeDriver();
            default -> {
                ChromeOptions o = new ChromeOptions();
                o.addArguments("--disable-notifications", "--remote-allow-origins=*");
                if (headless) o.addArguments("--headless=new", "--window-size=1920,1080");
                driver = new ChromeDriver(o);
            }
        }
        if (!headless) driver.manage().window().maximize();
        driver.get(Config.BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
