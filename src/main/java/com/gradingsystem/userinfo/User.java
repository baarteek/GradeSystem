package com.gradingsystem.userinfo;

public class User {
    private static String accountType;

    public static void setType(String newtype) {
        accountType = newtype;
    }

    public static String getType() {
        return accountType;
    }
}
