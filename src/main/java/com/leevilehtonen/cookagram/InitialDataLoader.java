package com.leevilehtonen.cookagram;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Role;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * Initial data loader creates one root user for application and one test user
 *
 * @author lleevi
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setup = false;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * When application event occurs, check whether initial data is loaded and if not creates some initial data
     *
     * @param contextRefreshedEvent
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (setup) return;
        createRole("ADMIN");
        createRole("USER");

        if (accountRepository.findByUsername("root") != null) {
            setup = true;
            return;
        }

        Account admin = new Account();
        admin.setUsername("root");
        admin.setEmail("root@root.com");
        admin.setFirstname("Root");
        admin.setLastname("Root");

        String password = System.getenv("ADMIN_PASSWORD");
        if (password == null) {
            password = "123456";
        }
        admin.setPassword(bCryptPasswordEncoder.encode(password));
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(roleRepository.findByName("ADMIN"));
        rolesAdmin.add(roleRepository.findByName("USER"));
        admin.setRoles(rolesAdmin);
        accountRepository.save(admin);

        Account user = new Account();
        user.setUsername("tester");
        user.setEmail("test@test.com");
        user.setFirstname("Test");
        user.setLastname("Test");

        user.setPassword(bCryptPasswordEncoder.encode("123456"));
        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(roleRepository.findByName("USER"));
        user.setRoles(rolesUser);
        accountRepository.save(user);
        setup = true;
    }

    /**
     * Create if does not exists a new role to database
     *
     * @param name name of the role
     * @return created or found role object
     */
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