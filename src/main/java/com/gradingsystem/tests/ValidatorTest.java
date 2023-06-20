package com.gradingsystem.tests;

import com.gradingsystem.utils.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void testValidateEmail_ValidEmail_ReturnsTrue() {
        String validEmail = "test@example.com";
        boolean isValid = Validator.validateEmail(validEmail);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidateEmail_InvalidEmail_ReturnsFalse() {
        String invalidEmail = "invalid_email";
        boolean isValid = Validator.validateEmail(invalidEmail);
        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidatePassword_ValidPassword_ReturnsTrue() {
        String validPassword = "zaq1@WSX!@#$";
        boolean isValid = Validator.validatePassword(validPassword);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidatePassword_InvalidPassword_ReturnsFalse() {
        String invalidPassword = "password";
        boolean isValid = Validator.validatePassword(invalidPassword);
        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidatePESEL_ValidPESEL_ReturnsTrue() {
        String validPESEL = "12345678901";
        boolean isValid = Validator.validatePESEL(validPESEL);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidatePESEL_InvalidPESEL_ReturnsFalse() {
        String invalidPESEL = "12345";
        boolean isValid = Validator.validatePESEL(invalidPESEL);
        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidatePhoneNumber_ValidPhoneNumber_ReturnsTrue() {
        String validPhoneNumber = "123456789";
        boolean isValid = Validator.validatePhoneNumber(validPhoneNumber);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidatePhoneNumber_InvalidPhoneNumber_ReturnsFalse() {
        String invalidPhoneNumber = "abcdefghi";
        boolean isValid = Validator.validatePhoneNumber(invalidPhoneNumber);
        Assert.assertFalse(isValid);
    }
}

