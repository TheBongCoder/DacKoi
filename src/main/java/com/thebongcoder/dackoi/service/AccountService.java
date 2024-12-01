package com.thebongcoder.dackoi.service;

import com.thebongcoder.dackoi.Roles;
import com.thebongcoder.dackoi.dto.SignUpRequestDTO;
import com.thebongcoder.dackoi.entity.*;
import com.thebongcoder.dackoi.enums.AvailableStatus;
import com.thebongcoder.dackoi.repository.ClinicRepository;
import com.thebongcoder.dackoi.repository.OTPRepository;
import com.thebongcoder.dackoi.repository.RoleRepository;
import com.thebongcoder.dackoi.repository.UserRepository;
import com.thebongcoder.dackoi.utils.AppConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.stream.IntStream;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ClinicRepository clinicRepository;

    private final ExternalAPIService externalAPIService;

    private final EmailService emailService;

    private final OTPRepository otpRepository;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public boolean checkMobileNumber(String phoneNumber) {
        log.info("Phone number received:: {}", phoneNumber);
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean checkEmail(String email) {
        log.info("email received:: {}", email);
        return userRepository.existsByEmail(email);
    }

    public boolean checkUserName(String userName) {
        log.info("userName received:: {}", userName);
        return userRepository.existsByUserName(userName);
    }


    public String signUp(SignUpRequestDTO signUpRequestDTO) throws ParseException {
        log.info("Sign up request received:: {}", signUpRequestDTO);
        Role role = roleRepository.findByNames(signUpRequestDTO.getRoles());
        User user = new User();
        user.setRole(role);
        user.setFullName(signUpRequestDTO.getFullName());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPhoneNumber(signUpRequestDTO.getPhoneNumber());
        user.setPassword(signUpRequestDTO.getPassword());
        user.setAddress(signUpRequestDTO.getAddress());
        user.setUserName(signUpRequestDTO.getUserName());
        user.setGender(signUpRequestDTO.getGender());
        Double latitude = signUpRequestDTO.getLatitude();
        Double longitude = signUpRequestDTO.getLongitude();

        WKTReader wktReader = new WKTReader();
        Geometry geometry = wktReader.read(AppConstant.POINT + longitude + " " + latitude + ")");
        geometry.setSRID(4326); // Set SRID
        Location location = new Location();
        location.setPoint(geometry);
        location.setUser(user);
        user.setLocation(location);
        log.info("Location:: {}", location);
        String avatarUrl = externalAPIService.generateAvatarUrl(user.getUserName());
        user.setProfilePic(avatarUrl);
        user = userRepository.save(user);
        log.info("User saved successfully:: {}", user);
        if (role.getNames().equals(Roles.ADMIN)) {
            Clinic clinic = new Clinic();
            clinic.setAvailableStatus(AvailableStatus.ACTIVE);
            clinic.setRating(0);
            clinic.setUserId(user.getId());
            clinicRepository.save(clinic);
            log.info("Clinic created successfully:: {}", clinic);
        }
        String generatedOTP = externalAPIService.generateOTP(4);
        emailService.sendOTP(user.getEmail(), user.getFullName(), generatedOTP);
        OTPDetail otpDetail = new OTPDetail(generatedOTP, user.getId());
        otpRepository.save(otpDetail);
        log.info("User created successfully:: {}", user);
        return "Account created successfully";
    }

}
