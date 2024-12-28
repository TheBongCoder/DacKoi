package com.thebongcoder.dackoi.entity;


import com.thebongcoder.dackoi.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    private String fullName;

    private String phoneNumber;

    private String email;

    private String password;

    private String address;

    private String userName;

    private String profilePic;

    @ManyToOne(optional = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Location location;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Doctor doctor;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Clinic clinic;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Patient patient;

}
