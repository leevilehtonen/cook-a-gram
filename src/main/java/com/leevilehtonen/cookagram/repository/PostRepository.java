package com.leevilehtonen.cookagram.repository;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Post repository
 *
 * @author lleevi
 */
public interface PostRepository extends CrudRepository<Post, Long> {

    /**
     * Finds posts where poster is in the target account list (used for making the feed functionality)
     * @param posters list of Accounts (friends)
     * @return List of posts
     */
    List<Post> findByPosterInOrderByDateDesc(List<Account> posters);

    /**
     * Finds posts of one user in descending date order (used for profile view)
     * @param poster target Account
     * @return List of posts
     */
    List<Post> findByPosterOrderByDateDesc(Account poster);

    /**
     * Finds the latest post by poster (used for profile picture)
     * @param poster target Account
     * @return One post
     */
    Post findTopByPosterOrderByDateDesc(Account poster);

    /**
     * Finds all posts in descending date order
     * @return List of posts
     */
    List<Post> findAllByOrderByDateDesc();

    /**
     * Finds the most liked posts
     * @return List of posts
     */
    @Query("SELECT p FROM Post p ORDER BY size(p.likes) DESC")
    List<Post> findMostLiked();

}
