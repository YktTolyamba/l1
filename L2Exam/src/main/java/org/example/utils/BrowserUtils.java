package org.example.utils;

import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.Cookie;

public class BrowserUtils {

    public static byte[] getScreenshot() {
        return AqualityServices.getBrowser().getScreenshot();
    }

    public static void maximize() {
        AqualityServices.getBrowser().maximize();
    }

    public static void goTo(String url) {
        AqualityServices.getBrowser().goTo(url);
    }

    public static void addToken(String token) {
        AqualityServices.getBrowser().getDriver().manage().addCookie(new Cookie("token", token));
    }

    public static void refresh() {
        AqualityServices.getBrowser().refresh();
    }

    public static void goBack() {
        AqualityServices.getBrowser().goBack();
    }

    public static void switchToLastTab() {
        AqualityServices.getBrowser().tabs().switchToLastTab();
    }

    public static void closeTab() {
        AqualityServices.getBrowser().tabs().closeTab();
    }
}
