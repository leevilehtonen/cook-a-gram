package com.leevilehtonen.cookagram.service;


import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Role;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));
        account.setRoles(roles);
        accountRepository.save(account);
    }

    public Account getAuthenticatedAccount() {
        return accountRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
