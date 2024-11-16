package com.thebongcoder.dackoi.repository;

import com.thebongcoder.dackoi.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
