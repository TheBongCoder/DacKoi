package com.thebongcoder.dackoi.controller;


import com.thebongcoder.dackoi.dto.SignUpRequestDTO;
import com.thebongcoder.dackoi.service.AccountService;
import com.thebongcoder.dackoi.service.CommonUtilService;
import com.thebongcoder.dackoi.utils.AppConstant;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UrlConstant.BASE_API)
@Slf4j
@AllArgsConstructor
public class AccountController {


    private final ResponseHandler responseHandler;

    private final AccountService accountService;

    private final CommonUtilService commonUtilService;


    @PostMapping(value = UrlConstant.SIGN_UP)
    public ResponseEntity<Object> signUp(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO, BindingResult bindingResult) {
        try {
            log.info("SIGNUP request received:: {}", signUpRequestDTO);
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            boolean existsMobileNumber = accountService.checkMobileNumber(signUpRequestDTO.getPhoneNumber());
            if (existsMobileNumber) {
                log.info("Phone number exists:: {}", signUpRequestDTO.getPhoneNumber());
                return responseHandler.sendResponse(existsMobileNumber, "Mobile number already exists", false, HttpStatus.IM_USED);
            }

            boolean checkEmail = accountService.checkEmail(signUpRequestDTO.getEmail());
            if (checkEmail) {
                log.info("Email exists:: {}", signUpRequestDTO.getEmail());
                return responseHandler.sendResponse(checkEmail, "Email already exists", false, HttpStatus.IM_USED);
            }

            boolean checkUserName = accountService.checkUserName(signUpRequestDTO.getUserName());
            if (checkUserName) {
                log.info("User name exists:: {}", signUpRequestDTO.getUserName());
                return responseHandler.sendResponse(signUpRequestDTO.getUserName() + AppConstant.ALREADY_EXISTS_TRY_ANOTHER_USER_NAME, "User name already exists", false, HttpStatus.IM_USED);
            }

            String userAccount = accountService.signUp(signUpRequestDTO);
            return responseHandler.sendResponse(userAccount, "Account created", true, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error for creating user: {} - Exception: {}", signUpRequestDTO.getUserName(), e.getMessage());
        }
        log.error("Error for creating user: {} - Exception: {}", signUpRequestDTO.getUserName(), "Something went wrong");
        return responseHandler.sendResponse(null, "Something Went Wrong", false, HttpStatus.BAD_REQUEST);
    }


}
