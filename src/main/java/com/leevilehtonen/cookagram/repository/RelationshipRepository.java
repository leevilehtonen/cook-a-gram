package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Relationship findTopByFollowerAndFollowed(Account follower, Account followed);

    Set<Relationship> findByFollower(Account follower);

    Set<Relationship> findByFollowed(Account followed);

}
