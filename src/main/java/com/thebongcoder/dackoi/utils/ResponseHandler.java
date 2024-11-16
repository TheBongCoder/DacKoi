package com.thebongcoder.dackoi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {

    public ResponseEntity<Object> sendResponse(Object data, String message, boolean isSuccess, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("message", message);
        response.put("isSuccess", isSuccess);
        return new ResponseEntity<>(response, httpStatus);
    }
}
