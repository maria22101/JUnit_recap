package com.virtualpairprogrammers.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTest {

    @Test
    public void checkTenDigitsAValidISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");

        assertTrue("first value", result);

        result = validator.checkISBN("0140177396");

        assertTrue("second value", result);
    }

    @Test
    public void tenDigitsISBNNumbersEndingInAnXAreValid() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("012000030X");

        assertTrue(result);
    }

    @Test
    public void checkThirteenDigitsAValidISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853260087");

        assertTrue("first value", result);

        result = validator.checkISBN("9781853267338");

        assertTrue("second value", result);
    }

    @Test
    public void checkAnInvalid10DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449117");

        assertFalse(result);
    }

    @Test
    public void checkAnInvalid13DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853267336");

        assertFalse(result);
    }

    @Test(expected = NumberFormatException.class)
    public void nineDigitISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void nonNumericISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("helloworld");
    }
}