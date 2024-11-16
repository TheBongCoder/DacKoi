package com.thebongcoder.dackoi;

import com.thebongcoder.dackoi.entity.Role;
import com.thebongcoder.dackoi.entity.User;
import com.thebongcoder.dackoi.repository.RoleRepository;
import com.thebongcoder.dackoi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createdRoleIfNotExist();
        createSuperAdmin();
    }

    private void createSuperAdmin() {
        if (!userRepository.existsByEmail("theBongCoder@gmail.com")) {
            User user = new User();
            user.setFullName("Super Admin");
            user.setAddress("India");
            user.setPhoneNumber("9325457345");
            user.setPassword("Test@123");
            user.setEmail("theBongCoder@gmail.com");
            Role role = roleRepository.findByNames(Roles.SUPER_ADMIN);
            user.setRole(role);
            userRepository.save(user);
        }
        log.info("createSuperAdmin");
    }

    public void createdRoleIfNotExist() {

        if (!roleRepository.existsByNames(Roles.SUPER_ADMIN)) {
            roleRepository.save(new Role(Roles.SUPER_ADMIN));
        }
        if (!roleRepository.existsByNames(Roles.USER)) {
            roleRepository.save(new Role(Roles.USER));
        }
        if (!roleRepository.existsByNames(Roles.ADMIN)) {
            roleRepository.save(new Role(Roles.ADMIN));
        }
        if (!roleRepository.existsByNames(Roles.DOCTOR)) {
            roleRepository.save(new Role(Roles.DOCTOR));
        }
        log.info("createdRoleIfNotExist");
    }
}
