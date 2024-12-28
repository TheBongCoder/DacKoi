package com.thebongcoder.dackoi.service;

import com.thebongcoder.dackoi.dto.ClinicResponseDTO;
import com.thebongcoder.dackoi.entity.User;
import com.thebongcoder.dackoi.repository.LocationRepository;
import com.thebongcoder.dackoi.repository.UserRepository;
import com.thebongcoder.dackoi.utils.AppConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;


    public List<ClinicResponseDTO> findNearestLocation(String email) {
        User currentUser = userRepository.findIdByEmail(email);
        Geometry geometry = locationRepository.findCoordinateByUserId(currentUser.getId());

        WKTWriter reader = new WKTWriter();
        String point = reader.write(geometry);

        List<Object[]> locationsDetails = locationRepository.findAllLocations(point, currentUser.getId());

        List<ClinicResponseDTO> clinicResponseDTOList = new ArrayList<>();

        mappedClinicRequestDetails(locationsDetails, clinicResponseDTOList);
        return clinicResponseDTOList;
    }

    private void mappedClinicRequestDetails(List<Object[]> locationsDetails, List<ClinicResponseDTO> clinicRequestDTOList) {
        for (Object[] locationsDetail : locationsDetails) {
            // convert BigInteger to Long
            BigInteger bigInteger = (BigInteger) locationsDetail[0];
            Optional<User> optionalUser = userRepository.findById(bigInteger.longValue());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Coordinate coordinate = user.getLocation().getPoint().getCoordinate();
                String distance = (double) locationsDetail[1] + AppConstant.KM;
                ClinicResponseDTO clinicResponseDTO = new ClinicResponseDTO(user.getFullName(), user.getAddress(),
                        user.getPhoneNumber(), user.getEmail(), coordinate, distance);
                clinicRequestDTOList.add(clinicResponseDTO);
            }
        }
    }

}
