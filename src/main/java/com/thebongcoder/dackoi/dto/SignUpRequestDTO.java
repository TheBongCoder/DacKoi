package com.thebongcoder.dackoi.dto;

import com.thebongcoder.dackoi.Roles;
import com.thebongcoder.dackoi.enums.Gender;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 30, message = "min 3 letters and max 30 letters allowed")
    private String fullName;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Enter valid mobile number")
    @Size(min = 10, max = 10, message = "Enter valid mobile number")
    private String phoneNumber;

    private String userName;

    private Gender gender;

    @Email(message = "Enter valid email address")
    private String email;

    @NotBlank
    @Size(min = 4, max = 8, message = "min 4 letters and max 8 letters allowed")
    private String password;

    private String address;

    @NotNull
    private Roles roles;

//    @NotBlank(message = "please enter address")
    private Double latitude;

//    @NotBlank(message = "please enter address")
    private Double longitude;
}
