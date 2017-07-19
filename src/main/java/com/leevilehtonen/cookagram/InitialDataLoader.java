package com.leevilehtonen.cookagram;

import com.leevilehtonen.cookagram.domain.Role;
import com.leevilehtonen.cookagram.domain.User;
import com.leevilehtonen.cookagram.repository.RoleRepository;
import com.leevilehtonen.cookagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (setup) return;
        createRole("ADMIN");
        createRole("USER");

        User user = new User();
        user.setUsername("root");
        user.setEmail("root@root.com");
        user.setFirstname("Root");
        user.setLastname("Root");

        String password = System.getenv("ADMIN_PASSWORD");
        if (password == null) {
            password = "123456";
        }
        user.setPassword(passwordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ADMIN"));
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);
        userRepository.save(user);

        setup = true;
    }

    @Transactional
    public Role createRole(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
        return role;
    }
}
