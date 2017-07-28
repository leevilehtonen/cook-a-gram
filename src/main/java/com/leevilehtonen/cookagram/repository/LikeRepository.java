package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.domain.ResourceLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LikeRepository extends JpaRepository<ResourceLike, Long> {

    ResourceLike findTopByLikerAndPost(Account liker, Post post);

    Set<ResourceLike> findByLiker(Account liker);
}
