package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.domain.ResourceLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Like repository
 *
 * @author lleevi
 */
public interface LikeRepository extends JpaRepository<ResourceLike, Long> {
    /**
     * Finds one by liker and post
     * @param liker target Account
     * @param post target Post
     * @return Like object
     */
    ResourceLike findTopByLikerAndPost(Account liker, Post post);

    /**
     * Finds all likes by user
     * @param liker target Account
     * @return Set of likes
     */
    Set<ResourceLike> findByLiker(Account liker);
}
