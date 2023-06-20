package com.gradingsystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Validator {
    private static final Logger logger = LogManager.getLogger(Validator.class);

    public static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validatePESEL(String pesel) {
        if(pesel.length() != 11) {
            logger.debug("wrong pesel");
            return false;
        }
        for(int i=0; i<pesel.length(); i++) {
            if(!Character.isDigit(pesel.charAt(i))) {
                logger.debug("wrong pesel");
                return false;
            }
        }

        return true;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 9) {
            logger.debug("wrong phone number");
            return false;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i)) && phoneNumber.charAt(i) != ' ') {
                logger.debug("wrong phone number");
                return false;
            }
        }

        return true;
    }

}

