package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByPosterInOrderByDateDesc(List<Account> posters);

    List<Post> findByPosterOrderByDateDesc(Account poster);

    Post findTopByPosterOrderByDateDesc(Account poster);

    List<Post> findAllByOrderByDateDesc();

    List<Post> findByOrderByLikesDesc();

    @Query("SELECT p FROM Post p ORDER BY size(p.likes) DESC")
    List<Post> findMostLiked();

}
