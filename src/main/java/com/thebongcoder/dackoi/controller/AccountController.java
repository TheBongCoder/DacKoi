package com.thebongcoder.dackoi.controller;


import com.thebongcoder.dackoi.dto.ClinicRequestDTO;
import com.thebongcoder.dackoi.dto.SignUpRequestPatientDTO;
import com.thebongcoder.dackoi.service.AccountService;
import com.thebongcoder.dackoi.service.CommonUtilService;
import com.thebongcoder.dackoi.utils.MessageConstant;
import com.thebongcoder.dackoi.utils.ResponseHandler;
import com.thebongcoder.dackoi.utils.UrlConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class AccountController {


    private final ResponseHandler responseHandler;

    private final AccountService accountService;

    private final CommonUtilService commonUtilService;


    @PostMapping(value = UrlConstant.SIGN_UP_PATIENT)
    public ResponseEntity<Object> signUpPatient(@Validated @RequestBody SignUpRequestPatientDTO signUpRequestPatientDTO, BindingResult bindingResult) {
        try {
            log.info("SIGNUP request received:: {}", signUpRequestPatientDTO);
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            boolean existsMobileNumber = accountService.checkMobileNumber(signUpRequestPatientDTO.getPhoneNumber());
            if (existsMobileNumber) {
                log.info("Phone number exists:: {}", signUpRequestPatientDTO.getPhoneNumber());
                return responseHandler.sendResponse(existsMobileNumber, "Mobile number already exists", false, HttpStatus.IM_USED);
            }

            boolean checkEmail = accountService.checkEmail(signUpRequestPatientDTO.getEmail());
            if (checkEmail) {
                log.info("Email exists:: {}", signUpRequestPatientDTO.getEmail());
                return responseHandler.sendResponse(checkEmail, "Email already exists", false, HttpStatus.IM_USED);
            }

            boolean checkUserName = accountService.checkUserName(signUpRequestPatientDTO.getUserName());
            if (checkUserName) {
                log.info("User name exists:: {}", signUpRequestPatientDTO.getUserName());
                return responseHandler.sendResponse(signUpRequestPatientDTO.getUserName() + MessageConstant.ALREADY_EXISTS_TRY_ANOTHER_USER_NAME, "User name already exists", false, HttpStatus.IM_USED);
            }

            String userAccount = accountService.signUpPatient(signUpRequestPatientDTO);
            return responseHandler.sendResponse(userAccount, "Account created", true, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error for creating user: {} - Exception: {}", signUpRequestPatientDTO.getUserName(), e.getMessage());
        }
        log.error("Error for creating user: {} - Exception: {}", signUpRequestPatientDTO.getUserName(), "Something went wrong");
        return responseHandler.sendResponse(null, "Something Went Wrong", false, HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = UrlConstant.REGISTER_CLINIC)
    public ResponseEntity<Object> registerClinic(@Validated @RequestBody ClinicRequestDTO clinicRequestDTO, BindingResult bindingResult) {
        try {
            log.info(" Register Clinic request received:: {}", clinicRequestDTO);
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            boolean existsMobileNumber = accountService.checkMobileNumber(clinicRequestDTO.getPhoneNumber());
            if (existsMobileNumber) {
                log.info("Clinic Phone number exists:: {}", clinicRequestDTO.getPhoneNumber());
                return responseHandler.sendResponse(existsMobileNumber, "Mobile number already exists", false, HttpStatus.IM_USED);
            }

            boolean checkEmail = accountService.checkEmail(clinicRequestDTO.getEmail());
            if (checkEmail) {
                log.info("Clinic Email exists:: {}", clinicRequestDTO.getEmail());
                return responseHandler.sendResponse(checkEmail, "Email already exists", false, HttpStatus.IM_USED);
            }

            boolean checkUserName = accountService.checkUserName(clinicRequestDTO.getUserName());
            if (checkUserName) {
                log.info("Clinic User name exists:: {}", clinicRequestDTO.getUserName());
                return responseHandler.sendResponse(clinicRequestDTO.getUserName() + MessageConstant.ALREADY_EXISTS_TRY_ANOTHER_USER_NAME, "User name already exists", false, HttpStatus.IM_USED);
            }

            String clinicAccount = accountService.registerClinic(clinicRequestDTO);
            return responseHandler.sendResponse(clinicAccount, "Account created", true, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error for creating user: {} - Exception: {}", clinicRequestDTO.getUserName(), e.getMessage());
        }
        log.error("Error for creating user: {} - Exception: {}", clinicRequestDTO.getUserName(), "Something went wrong");
        return responseHandler.sendResponse(null, "Something Went Wrong", false, HttpStatus.BAD_REQUEST);
    }

}
