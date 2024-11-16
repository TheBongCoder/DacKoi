package com.thebongcoder.dackoi.service;

import com.thebongcoder.dackoi.dto.ClinicRequestDTO;
import com.thebongcoder.dackoi.dto.SignUpRequestDTO;
import com.thebongcoder.dackoi.entity.Location;
import com.thebongcoder.dackoi.entity.Role;
import com.thebongcoder.dackoi.entity.User;
import com.thebongcoder.dackoi.repository.LocationRepository;
import com.thebongcoder.dackoi.repository.RoleRepository;
import com.thebongcoder.dackoi.repository.UserRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LocationRepository locationRepository;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public boolean checkMobileNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }


    public String signUp(SignUpRequestDTO signUpRequestDTO) throws ParseException {
        User user = new User();
        user.setFullName(signUpRequestDTO.getFullName());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPhoneNumber(signUpRequestDTO.getPhoneNumber());
        user.setPassword(signUpRequestDTO.getPassword());
        user.setAddress(signUpRequestDTO.getAddress());
        Role role = roleRepository.findByNames(signUpRequestDTO.getRoles());
        user.setRole(role);
        Double latitude = signUpRequestDTO.getLatitude();
        Double longitude = signUpRequestDTO.getLongitude();

        WKTReader wktReader = new WKTReader();
        Geometry geometry = wktReader.read("POINT (" + longitude + " " + latitude + ")");
        geometry.setSRID(4326); // Set SRID
        Location location = new Location();
        location.setPoint(geometry);
        location.setUser(user);
        user.setLocation(location);
        userRepository.save(user);
        return "Account created successfully";
    }


    public List<ClinicRequestDTO> findNearestlocation(String email) {
        User currentUser = userRepository.findIdByEmail(email);
        Geometry geometry = locationRepository.findCoordinateByUserId(currentUser.getId());

        WKTWriter reader = new WKTWriter();
        String point = reader.write(geometry);

        List<Object[]> locationsDetails = locationRepository.findAllLocations(point, currentUser.getId());

        List<ClinicRequestDTO> clinicRequestDTOList = new ArrayList<>();

        for (Object[] objects : locationsDetails) {
            ClinicRequestDTO clinicRequestDTO = new ClinicRequestDTO();
            // convert BigInteger to Long
            BigInteger bigInteger = (BigInteger) objects[0];
            Optional<User> optionalUser = userRepository.findById(bigInteger.longValue());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                clinicRequestDTO.setName(user.getFullName());
                clinicRequestDTO.setAddress(user.getAddress());
                clinicRequestDTO.setPhoneNumber(user.getPhoneNumber());
                clinicRequestDTO.setEmail(user.getEmail());
                Coordinate coordinate = user.getLocation().getPoint().getCoordinate();
                clinicRequestDTO.setCoordinate(coordinate);
                clinicRequestDTO.setDistance((double) objects[1] + " km");
                clinicRequestDTOList.add(clinicRequestDTO);
            }

        }

        return clinicRequestDTOList;
    }
}
