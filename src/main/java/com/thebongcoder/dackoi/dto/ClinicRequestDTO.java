package com.thebongcoder.dackoi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.locationtech.jts.geom.Coordinate;

@Data
@AllArgsConstructor
public class ClinicRequestDTO {

    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private Coordinate coordinate;
    private String distance;
}
