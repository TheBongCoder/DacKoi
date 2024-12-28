package com.thebongcoder.dackoi.entity;

import com.thebongcoder.dackoi.enums.AvailableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clinic extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @ManyToMany(mappedBy = "clinics")
    private Set<Doctor> doctors;

    @Enumerated(EnumType.STRING)
    private AvailableStatus availableStatus;

    private Integer rating;
}
