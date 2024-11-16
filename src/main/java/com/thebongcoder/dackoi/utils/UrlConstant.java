package com.thebongcoder.dackoi.utils;

public final class UrlConstant {

    private UrlConstant() {
        throw new IllegalStateException("URLConstant class");
    }

    public static final String BASE_API = "/api/v1/";

    public static final String WELCOME = BASE_API + "welcome";
    public static final String SIGN_UP = "signUp";
    public static final String FIND_NEAREST_LOCATION = "findNearestLocation";
}
