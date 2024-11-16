package com.thebongcoder.dackoi.repository;

import com.thebongcoder.dackoi.entity.Location;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {


    @Query(value = "select point from Location where user_id = :userId")
    Geometry findCoordinateByUserId(Long userId);

    @Query(value = "SELECT user_id, ST_Distance_Sphere(ST_SRID(ST_GeomFromText(:geometry), 4326), coordinate) / 1000 AS distance " +
            "FROM location WHERE user_id != :userId HAVING distance < 7 ORDER BY distance;", nativeQuery = true)
    List<Object[]> findAllLocations(@Param("geometry") String geometry, @Param("userId") Long userId);

}
