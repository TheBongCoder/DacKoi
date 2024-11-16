package com.thebongcoder.dackoi.controller;


import com.thebongcoder.dackoi.dto.ClinicRequestDTO;
import com.thebongcoder.dackoi.dto.SignUpRequestDTO;
import com.thebongcoder.dackoi.service.AccountService;
import com.thebongcoder.dackoi.service.CommonUtilService;
import com.thebongcoder.dackoi.utils.ResponseHandler;
import com.thebongcoder.dackoi.utils.UrlConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UrlConstant.BASE_API)
@Slf4j
public class AccountController {

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CommonUtilService commonUtilService;


    @PostMapping(value = UrlConstant.SIGN_UP)
    public ResponseEntity<Object> signUp(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO, BindingResult bindingResult) {
        try {
            log.info("SIGNUP request received:: {}", signUpRequestDTO);
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            boolean existsMobileNumber = accountService.checkMobileNumber(signUpRequestDTO.getPhoneNumber());
            if (existsMobileNumber) {
                return responseHandler.sendResponse(existsMobileNumber, "Mobile number already exists", false, HttpStatus.IM_USED);
            }
            String userAccount = accountService.signUp(signUpRequestDTO);
            return responseHandler.sendResponse(userAccount, "Account created", true, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Exception :", e);
        }

        return responseHandler.sendResponse(null, "Something Went Wrong", false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = UrlConstant.FIND_NEAREST_LOCATION)
    public ResponseEntity<Object> findNearestLocation(@RequestParam String email) {
        try {
            List<ClinicRequestDTO> nearestlocation = accountService.findNearestlocation(email);
            if (!nearestlocation.isEmpty()) {
                return responseHandler.sendResponse(nearestlocation, "NearestLocation Fetched", false, HttpStatus.IM_USED);
            }
        } catch (Exception e) {
            log.info("Exception :", e);
        }
        return responseHandler.sendResponse(null, "Something Went Wrong", false, HttpStatus.BAD_REQUEST);
    }


}
