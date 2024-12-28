package com.thebongcoder.dackoi.utils;

public class ValidationConstant {

    private ValidationConstant() {

        throw new IllegalStateException("ValidationConstant class");
    }

    public static final String NAME_CANNOT_BE_BLANK = "Name cannot be blank";
    public static final String ENTER_VALID_MOBILE_NUMBER = "Enter valid mobile number";
    public static final String MIN_4_LETTERS_AND_MAX_8_LETTERS_ALLOWED = "min 4 letters and max 8 letters allowed";
    public static final String MIN_3_LETTERS_AND_MAX_30_LETTERS_ALLOWED = "min 3 letters and max 30 letters allowed";

}
