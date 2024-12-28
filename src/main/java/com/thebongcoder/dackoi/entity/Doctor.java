package com.thebongcoder.dackoi.entity;

import com.thebongcoder.dackoi.enums.AvailableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor extends BaseEntity {

    private String description;

    @Enumerated(EnumType.STRING)
    private AvailableStatus availableStatus;

    private Integer rating;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "doctor",
            cascade = ALL,
            orphanRemoval = true
    )
    private Set<DoctorSpecializeDetail> doctorSpecializeDetails;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "doctor_clinic",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "clinic_id")
    )
    private Set<Clinic> clinics;

}
