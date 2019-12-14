package com.akash.myfirstunittestapp;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue(){
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue(){
        assertTrue(EmailValidator.isValidEmail("name@email.co.in"));
    }

    @Test
    public void emailValidator_InvalidEmailNoDom_ReturnsFalse(){
        assertFalse(EmailValidator.isValidEmail("name@email"));
    }

    @Test
    public void emailValidator_InvalidEmailMultiDots_ReturnsFalse(){
        assertFalse(EmailValidator.isValidEmail("name@email..com"));
    }

    @Test
    public void emailValidator_InvalidEmailNoUserName_ReturnsFalse(){
        assertFalse(EmailValidator.isValidEmail("@email.com"));
    }

    @Test
    public void emailValidator_InvalidEmailEmptyString_ReturnsFalse(){
        assertFalse(EmailValidator.isValidEmail(""));
    }

    @Test
    public void emailValidator_InvalidEmailNull_ReturnsFalse(){
        assertFalse(EmailValidator.isValidEmail(null));
    }

}
