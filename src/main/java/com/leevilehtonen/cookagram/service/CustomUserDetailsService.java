package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Role;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom user details service used for authentication. This service connects the authentication system to the database
 *
 * @author lleevi
 * @see UserDetailsService
 * @see com.leevilehtonen.cookagram.config.ProductionSecurityConfiguration
 * @see com.leevilehtonen.cookagram.config.DefaultSecurityConfiguration
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Loads Account from the database and returns security user object from that
     * @param username Username of the account to be looked for
     * @return User object which is cretaed from database Account object
     * @throws UsernameNotFoundException in case user is not found exception is thrown
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), getGrantedAuthorities(new ArrayList<>(account.getRoles())));

    }

    /**
     * Converts database roles to GrantedAuthorities
     * @param roles list of roles from database
     * @return list of GrantedAuthorities
     */
    private List<GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role r : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(r.getName()));
        }
        return grantedAuthorities;
    }
}
