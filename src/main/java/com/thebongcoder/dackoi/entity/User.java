package com.thebongcoder.dackoi.entity;


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

    /*@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    // name = "role_id": This defines the foreign key in the User table that will store the Role's id.
    private Role role;   */

    @ManyToOne(optional = false)
    private Role role;// referencedColumnName = "id": Specifies that the id from the Role entity will be the referenced column.

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Location location;
}
