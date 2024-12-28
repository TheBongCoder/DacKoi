package com.thebongcoder.dackoi.controller;

import com.thebongcoder.dackoi.dto.ClinicResponseDTO;
import com.thebongcoder.dackoi.service.CommonUtilService;
import com.thebongcoder.dackoi.service.UserService;
import com.thebongcoder.dackoi.utils.ResponseHandler;
import com.thebongcoder.dackoi.utils.UrlConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = UrlConstant.BASE_API)
@Slf4j
@AllArgsConstructor
public class UserController {

    private final ResponseHandler responseHandler;

    private final UserService userService;

    private final CommonUtilService commonUtilService;


    @GetMapping(value = UrlConstant.FIND_NEAREST_LOCATION)
    public ResponseEntity<Object> findNearestLocation(@RequestParam String email) {
        try {
            List<ClinicResponseDTO> nearestLocation = userService.findNearestLocation(email);
            if (!nearestLocation.isEmpty()) {
                return responseHandler.sendResponse(nearestLocation, "NearestLocation Fetched", false, HttpStatus.IM_USED);
            }
        } catch (Exception e) {
            log.info("Exception :", e);
        }
        return responseHandler.sendResponse(null, "Something Went Wrong", false, HttpStatus.BAD_REQUEST);
    }
}
