package com.leevilehtonen.cookagram.service;


import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.domain.Relationship;
import com.leevilehtonen.cookagram.domain.Role;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import com.leevilehtonen.cookagram.repository.RelationshipRepository;
import com.leevilehtonen.cookagram.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        account.setRoles(roles);
        accountRepository.save(account);
    }

    public Account getAuthenticatedAccount() {
        return accountRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Transactional
    public String getProfilePictureUrl(Account account) {
        if (account.getPosts().size() > 0) {
            Post p = postRepository.findTopByPosterOrderByDateDesc(account);

            return "/images/" + p.getImage().getId();
        } else {
            return "img/profile.jpg";
        }
    }

    public Set<Account> getFollowers(Account account) {
        Set<Account> accounts = new HashSet<>();
        Set<Relationship> relationships = relationshipRepository.findByFollowed(account);
        if (relationships == null) {
            return accounts;
        }
        relationships.forEach(relationship -> accounts.add(relationship.getFollower()));
        return accounts;
    }

    public Set<Account> getFollowings(Account account) {
        Set<Account> accounts = new HashSet<>();
        Set<Relationship> relationships = relationshipRepository.findByFollower(account);
        if (relationships == null) {
            return accounts;
        }
        relationships.forEach(relationship -> accounts.add(relationship.getFollower()));
        return accounts;
    }
}
