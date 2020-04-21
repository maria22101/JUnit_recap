package com.virtualpairprogrammers.isbntools;

public class ValidateISBN {

    public static final int LONG_ISBN_LENGTH = 13;
    public static final int SHORT_ISBN_LENGTH = 10;
    public static final int SHORT_ISBN_MULTIPLIER = 11;
    public static final int LONG_ISBN_MULTIPLIER = 10;
    public static final String ISBN_NUMBER_LENGTH_INSTRUCTION = "ISBN number must be 10 or 13 digits long";
    public static final String ISBN_NUMBER_CONTENT_INSTRUCTION
            = "ISBN number can only contain numeric digits except for \"X\" on the last place ";

    public boolean checkISBN(String isbn) {
        if (isbn.length() == LONG_ISBN_LENGTH) {
            return isThisAValidLongISBN(isbn);
        }
        if (isbn.length() == SHORT_ISBN_LENGTH) {
            return isThisAValidShortISBN(isbn);
        }
        throw new NumberFormatException(ISBN_NUMBER_LENGTH_INSTRUCTION);
    }

    //A short valid ISBN consists of 10 digits or 9 digits + 'X';
    //when each of the digit is multiplied by 10, 9 ..., 1 and summed up,
    //for it to be valid it is to be divisible by 11 without a reminder.
    //In case of an 'X' at the end the number component is 10.
    private boolean isThisAValidShortISBN(String isbn) {
        int total = 0;

        for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                if (i == (SHORT_ISBN_LENGTH - 1) && isbn.charAt(i) == 'X') {
                    total += 10;
                } else {
                    throw new NumberFormatException(ISBN_NUMBER_CONTENT_INSTRUCTION);
                }
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN_LENGTH - i);
            }
        }

        return total % SHORT_ISBN_MULTIPLIER == 0;
    }

    //A long valid ISBN consists of 13 digits
    //when each of the digit is multiplied by 1, 3, 1, 3 ... and summed up,
    //for it to be valid it is to be divisible by 10 without a reminder.
    private boolean isThisAValidLongISBN(String isbn) {
        int total = 0;
        int multiplicator;

        for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
            multiplicator = 1;
            if (i % 2 != 0) {
                multiplicator = 3;
            }
            total += Character.getNumericValue(isbn.charAt(i)) * multiplicator;
        }

        return total % LONG_ISBN_MULTIPLIER == 0;
    }
}
