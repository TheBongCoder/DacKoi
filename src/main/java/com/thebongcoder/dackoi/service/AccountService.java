package com.thebongcoder.dackoi.service;

import com.thebongcoder.dackoi.Roles;
import com.thebongcoder.dackoi.dto.ClinicRequestDTO;
import com.thebongcoder.dackoi.dto.SignUpRequestPatientDTO;
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


    public String signUpPatient(SignUpRequestPatientDTO signUpRequestPatientDTO) throws ParseException {
        log.info("Sign up request received:: {}", signUpRequestPatientDTO);
        Role role = roleRepository.findByNames(signUpRequestPatientDTO.getRoles());
        User user = new User();
        user.setRole(role);
        user.setFullName(signUpRequestPatientDTO.getFullName());
        user.setEmail(signUpRequestPatientDTO.getEmail());
        user.setPhoneNumber(signUpRequestPatientDTO.getPhoneNumber());
        user.setPassword(signUpRequestPatientDTO.getPassword());
        user.setAddress(signUpRequestPatientDTO.getAddress());
        user.setUserName(signUpRequestPatientDTO.getUserName());
        Double latitude = signUpRequestPatientDTO.getLatitude();
        Double longitude = signUpRequestPatientDTO.getLongitude();

        Location location = getUserLocation(longitude, latitude, user);
        user.setLocation(location);
        log.info("Location:: {}", location);
        String avatarUrl = externalAPIService.generateAvatarUrl(user.getUserName());
        user.setProfilePic(avatarUrl);
        user = userRepository.save(user);
        log.info("User saved successfully:: {}", user);
        String generatedOTP = externalAPIService.generateOTP(4);
        emailService.sendOTP(user.getEmail(), user.getFullName(), generatedOTP);
        OTPDetail otpDetail = new OTPDetail(generatedOTP, user.getId());
        otpRepository.save(otpDetail);
        log.info("User created successfully:: {}", user);
        return "Account created successfully";
    }

    private static Location getUserLocation(Double longitude, Double latitude, User user) throws ParseException {
        WKTReader wktReader = new WKTReader();
        Geometry geometry = wktReader.read(AppConstant.POINT + longitude + " " + latitude + ")");
        geometry.setSRID(4326); // Set SRID
        Location location = new Location();
        location.setPoint(geometry);
        location.setUser(user);
        log.info("{getUserLocation}Location:: {}", location);
        return location;
    }

    public String registerClinic(ClinicRequestDTO clinicRequestDTO) throws ParseException {
        log.info("Register clinic request received:: {}", clinicRequestDTO);
        User user = addUserDetailsFromClinicRequestDTO(clinicRequestDTO);
        Clinic clinic = addClinicDetails(user);
        user.setClinic(clinic);
        user = userRepository.save(user);
        log.info("User saved successfully:: {}", user);
        String generatedOTP = externalAPIService.generateOTP(4);
        /*emailService.sendOTP(user.getEmail(), user.getFullName(), generatedOTP);
        OTPDetail otpDetail = new OTPDetail(generatedOTP, user.getId());
        otpRepository.save(otpDetail);*/
        log.info("{registerClinic}User created successfully:: {}", user);
        return "Account created successfully";

    }

    private Clinic addClinicDetails(User user) {
        Clinic clinic = new Clinic();
        clinic.setUser(user);
        clinic.setAvailableStatus(AvailableStatus.ACTIVE);
        clinic.setRating(0);
        log.info("{addClinicDetails}Clinic created successfully:: {}", clinic);
//        clinicRepository.save(clinic);
        return clinic;
    }

    private User addUserDetailsFromClinicRequestDTO(ClinicRequestDTO clinicRequestDTO) throws ParseException {
        User user = new User();
        user.setFullName(clinicRequestDTO.getName());
        user.setAddress(clinicRequestDTO.getAddress());
        user.setPhoneNumber(clinicRequestDTO.getPhoneNumber());
        user.setEmail(clinicRequestDTO.getEmail());
        user.setPassword(clinicRequestDTO.getPassword());
        user.setUserName(clinicRequestDTO.getName());
        Role role = roleRepository.findByNames(Roles.ADMIN);
        user.setRole(role);
        Double latitude = clinicRequestDTO.getLatitude();
        Double longitude = clinicRequestDTO.getLongitude();
        Location location = getUserLocation(longitude, latitude, user);
        user.setLocation(location);
        log.info("Location:: {}", location);
        String avatarUrl = externalAPIService.generateAvatarUrl(user.getUserName());
        user.setProfilePic(avatarUrl);

        log.info("{addUserDetailsFromClinicRequestDTO}User created successfully:: {}", user);
        return user;
    }
}
