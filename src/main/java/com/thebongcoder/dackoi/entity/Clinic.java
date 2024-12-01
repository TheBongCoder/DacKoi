package com.thebongcoder.dackoi.entity;

import com.thebongcoder.dackoi.enums.AvailableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clinic extends BaseEntity {

    private Long userId;

    @ManyToMany(mappedBy = "clinics")
    private Set<Doctor> doctors;

    @Enumerated(EnumType.STRING)
    private AvailableStatus availableStatus;

    private Integer rating;
}
