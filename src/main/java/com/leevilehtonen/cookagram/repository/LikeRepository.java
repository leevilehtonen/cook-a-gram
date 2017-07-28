package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Like;
import com.leevilehtonen.cookagram.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findTopByLikerAndPost(Account liker, Post post);

    Set<Like> findByLiker(Account liker);
}
