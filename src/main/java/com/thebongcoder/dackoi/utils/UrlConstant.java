package com.thebongcoder.dackoi.utils;

public final class UrlConstant {

    private UrlConstant() {
        throw new IllegalStateException("URLConstant class");
    }

    public static final String BASE_API = "/api/v1/";

    public static final String SIGN_UP_PATIENT = BASE_API + "signUpPatient";
    public static final String REGISTER_CLINIC = BASE_API + "registerClinic";
    public static final String FIND_NEAREST_LOCATION = BASE_API + "findNearestLocation";
}
