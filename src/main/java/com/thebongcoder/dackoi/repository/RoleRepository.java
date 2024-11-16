package com.thebongcoder.dackoi.repository;

import com.thebongcoder.dackoi.Roles;
import com.thebongcoder.dackoi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNames(Roles roles);

    boolean existsByNames(Roles role);
}
