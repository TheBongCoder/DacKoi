package com.thebongcoder.dackoi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location extends BaseEntity {

    /*@Column(columnDefinition = "GEOMETRY")
//    @Type(type = "org.hibernate.spatial.GeometryType") // Specify the GeometryType
    private Geometry point;*/

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "COORDINATE")
    private Geometry point;
}
