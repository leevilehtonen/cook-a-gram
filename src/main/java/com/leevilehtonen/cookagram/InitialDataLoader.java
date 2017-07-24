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

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean setup = false;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

        Account account = new Account();
        account.setUsername("root");
        account.setEmail("root@root.com");
        account.setFirstname("Root");
        account.setLastname("Root");

        String password = System.getenv("ADMIN_PASSWORD");
        if (password == null) {
            password = "123456";
        }
        account.setPassword(bCryptPasswordEncoder.encode(password));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ADMIN"));
        roles.add(roleRepository.findByName("USER"));
        account.setRoles(roles);
        accountRepository.save(account);
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