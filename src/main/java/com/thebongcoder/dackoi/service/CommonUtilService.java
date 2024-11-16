package com.thebongcoder.dackoi.service;

import com.thebongcoder.dackoi.utils.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Slf4j
@Service
@AllArgsConstructor
public class CommonUtilService {


    private final ResponseHandler responseHandler;

    public ResponseEntity<Object> requestValidation(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append(". ");
        }
        log.info("Error in request:: {}", errorMessage);
        return responseHandler.sendResponse("", errorMessage.toString(), false,
                HttpStatus.BAD_REQUEST);
    }


}
