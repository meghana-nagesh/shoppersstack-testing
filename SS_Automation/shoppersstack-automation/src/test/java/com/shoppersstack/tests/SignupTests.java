package com.shoppersstack.tests;

import com.shoppersstack.base.BaseTest;
import com.shoppersstack.pages.SignupPage;
import com.shoppersstack.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class SignupTests extends BaseTest {

    private String uniquePhone() { return "9" + (100000000 + ThreadLocalRandom.current().nextInt(899999999)); }

    @Test(description = "SS_TC_003 | Register a new shopper with valid details", groups = {"system", "functional", "regression"})
    public void registerNewShopper() {
        String email = "auto" + System.currentTimeMillis() + "@gmail.com";
        SignupPage sp = new SignupPage(driver).open().fill("Autouser", "Tester", uniquePhone(), email, "Test@12345", "Test@12345", true);
        Assert.assertTrue(sp.isRegisterEnabled(), "Register button is still disabled - a field is invalid or Terms not ticked");
        sp.clickRegister();
        boolean still = true;
        for (int i = 0; i < 20 && still; i++) {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            still = sp.isOnSignupForm();
        }
        Assert.assertFalse(still, "Signup form still displayed - account was not created");
    }
    @Test(description = "SS_TC_004 | Register with an already registered email", groups = {"functional", "negative"})
    public void registerDuplicateEmail() {
        SignupPage sp = new SignupPage(driver).open().fill("Abcde", "sdfuid", uniquePhone(), Config.SHOPPER_EMAIL, "Test@12345", "Test@12345", true);
        sp.clickRegister();
        Assert.assertTrue(sp.isOnSignupForm(), "Duplicate registration should be blocked");
    }

    @Test(description = "SS_TC_005 | Register with blank mandatory fields", groups = {"functional", "negative", "ui"})
    public void registerBlankFields() {
        SignupPage sp = new SignupPage(driver).open();
        Assert.assertFalse(sp.isRegisterEnabled(), "Register must be disabled when mandatory fields are blank");
    }

    @Test(description = "SS_TC_006 | Register with mismatched password and confirm password", groups = {"functional", "negative", "regression"})
    public void registerPasswordMismatch() {
        SignupPage sp = new SignupPage(driver).open().fill("yetrujs", "sdguyt", uniquePhone(),
                "mm" + System.currentTimeMillis() + "@gmail.com", "CU&^#83", "#^8gGeu", true);
        if (sp.isRegisterEnabled()) sp.clickRegister();
        Assert.assertTrue(sp.isOnSignupForm(), "Registration must be prevented for mismatched passwords");
    }

    @Test(description = "SS_TC_007 | Register without accepting Terms and Conditions", groups = {"functional", "negative", "ui"})
    public void registerWithoutTerms() {
        SignupPage sp = new SignupPage(driver).open().fill("yetrujs", "sdguyt", uniquePhone(),
                "nt" + System.currentTimeMillis() + "@gmail.com", "CU&^#83", "CU&^#83", false);
        Assert.assertFalse(sp.isRegisterEnabled(), "Register must stay disabled until T&C is accepted");
    }
}
