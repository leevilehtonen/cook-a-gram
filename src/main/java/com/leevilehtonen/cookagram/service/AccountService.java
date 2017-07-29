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

/**
 * Account service for account related functionality
 *
 * @author lleevi
 */
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

    /**
     * Saves account which is passed from signup form. Uses BCrypt to encode the password and assigns the default user role before saving to database
     *
     * @param account Account to save
     */
    public void save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        account.setRoles(roles);
        accountRepository.save(account);
    }

    /**
     * Gets the currently authenticated account from security context
     * @return Account that is currently logged in
     */
    public Account getAuthenticatedAccount() {
        return accountRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Gets the profile picture of the account. If account has any posts, uses the latest one as a profile picture, otherwise loads the default profile picture
     * @param account Account which profile picture is looked for
     * @return image path
     */
    @Transactional
    public String getProfilePictureUrl(Account account) {
        if (account.getPosts().size() > 0) {
            Post p = postRepository.findTopByPosterOrderByDateDesc(account);

            return "/images/" + p.getImage().getId();
        } else {
            return "img/profile.jpg";
        }
    }

    /**
     * Gets the followers of the target account
     * @param account target account
     * @return set of followers
     */
    public Set<Account> getFollowers(Account account) {
        Set<Account> accounts = new HashSet<>();
        Set<Relationship> relationships = relationshipRepository.findByFollowed(account);
        if (relationships == null) {
            return accounts;
        }
        relationships.forEach(relationship -> accounts.add(relationship.getFollower()));
        return accounts;
    }

    /**
     * Get the accounts that target account if following
     * @param account
     * @return set of accounts that are following target account
     */
    public Set<Account> getFollowings(Account account) {
        Set<Account> accounts = new HashSet<>();
        Set<Relationship> relationships = relationshipRepository.findByFollower(account);
        if (relationships == null) {
            return accounts;
        }
        relationships.forEach(relationship -> accounts.add(relationship.getFollowed()));
        return accounts;
    }
}
