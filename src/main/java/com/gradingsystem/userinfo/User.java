package com.gradingsystem.userinfo;

public class User {
    private static String accountType;
    private static String cssFileName;

    public static void setType(String newtype) {
        accountType = newtype;
    }

    public static String getType() {
        return accountType;
    }

    public static void setCssFileName(String newCssFileName) {
        cssFileName = newCssFileName;
    }

    public static String getCssFileName() {
        return cssFileName;
    }
}
