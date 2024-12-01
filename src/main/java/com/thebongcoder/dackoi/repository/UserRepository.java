package com.thebongcoder.dackoi.repository;

import com.thebongcoder.dackoi.entity.User;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String mail);


    User findIdByEmail(String email);

    boolean existsByUserName(String userName);
}
