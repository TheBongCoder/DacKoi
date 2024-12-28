package com.thebongcoder.dackoi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.thebongcoder.dackoi.utils.ValidationConstant.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicRequestDTO {

    @NotBlank(message = NAME_CANNOT_BE_BLANK)
    @Size(min = 3, max = 30, message = MIN_3_LETTERS_AND_MAX_30_LETTERS_ALLOWED)
    private String name;

    private String address;


    @Pattern(regexp = "(^$|[0-9]{10})", message = ENTER_VALID_MOBILE_NUMBER)
    private String phoneNumber;

    //    @Email(message = "Enter valid email address")
    private String email;

    private String userName;

    @NotBlank
    @Size(min = 4, max = 8, message = MIN_4_LETTERS_AND_MAX_8_LETTERS_ALLOWED)
    private String password;

    //    @NotBlank(message = "please enter address")
    private Double latitude;

    //    @NotBlank(message = "please enter address")
    private Double longitude;
}
