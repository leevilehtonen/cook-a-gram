package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Relationship;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Relationship service for relationship related functionality
 *
 * @author lleevi
 */
@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Toggles relationship (if user is following the target, then unfollows or opposite)
     *
     * @param target id of the target account
     */
    @Transactional
    public void toggleRelationship(Long target) {
        Account followed = accountRepository.findOne(target);
        Account follower = accountService.getAuthenticatedAccount();
        Relationship relationship = relationshipRepository.findTopByFollowerAndFollowed(follower, followed);
        if (relationship == null) {
            relationship = new Relationship();
            relationship.setFollower(follower);
            relationship.setFollowed(followed);
            relationshipRepository.save(relationship);
            follower.getFollowings().add(relationship);
            accountRepository.save(follower);
        } else {
            follower.getFollowings().remove(relationship);
            relationshipRepository.delete(relationship);
        }

    }
}
