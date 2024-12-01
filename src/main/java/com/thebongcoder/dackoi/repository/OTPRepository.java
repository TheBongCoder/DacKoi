package com.thebongcoder.dackoi.repository;

import com.thebongcoder.dackoi.entity.OTPDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTPDetail, Long> {
}