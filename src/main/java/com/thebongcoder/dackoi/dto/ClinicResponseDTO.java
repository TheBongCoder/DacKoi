package com.thebongcoder.dackoi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicResponseDTO {


    private String name;

    private String address;


    private String phoneNumber;

    private String email;

    private Coordinate coordinate;

    private String distance;
}
