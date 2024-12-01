package com.thebongcoder.dackoi.entity;

import com.thebongcoder.dackoi.enums.DoctorSpecializations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSpecializeDetail extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private DoctorSpecializations doctorSpecializations;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

}
