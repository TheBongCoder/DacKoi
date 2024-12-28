/*
package com.thebongcoder.dackoi.service;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    private static final int EXPIRATION_TIME = 3;

    private LoadingCache<String, String> otpCache;

    public OTPService() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String s) {
                        return "";
                    }
                });
    }

    public String generateOTP(String email) {
        String otp = String.valueOf(new SecureRandom().nextInt(9000) + 1000);
        otpCache.put(email, otp);
        return otp;
    }

    public String getCachedOTP(String email) {
        try {
            return otpCache.get(email);
        } catch (Exception e) {
            return "";
        }
    }

    public void clearOTP(String email) {
        otpCache.invalidate(email);
    }

}
*/
