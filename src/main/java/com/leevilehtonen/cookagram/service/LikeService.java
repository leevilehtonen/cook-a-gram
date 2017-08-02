package com.leevilehtonen.cookagram.service;


import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.domain.ResourceLike;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.LikeRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * Like service for liking related functionality
 *
 * @author lleevi
 */
@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Toggles a like in a post (if account has liked it, it will be unliked or opposite)
     *
     * @param target target post
     */
    @Transactional
    public void toggleLike(Account liker, Long target) {
        Post post = postRepository.findOne(target);
        ResourceLike like = likeRepository.findTopByLikerAndPost(liker, post);
        if (like == null) {
            like = new ResourceLike();
            like.setLiker(liker);
            like.setPost(post);
            like = likeRepository.save(like);
            post.getLikes().add(like);
            liker.getLikes().add(like);
            postRepository.save(post);
            accountRepository.save(liker);

        } else {
            liker.getLikes().remove(like);
            post.getLikes().remove(like);
            likeRepository.delete(like);
        }

    }

    /**
     * Get the ids of posts that the account has liked
     * @param account target account
     * @return set of ids
     */
    public Set<Long> getAccountLikedPostIds(Account account) {
        Set<Long> likeIds = new HashSet<>();
        Set<ResourceLike> likes = likeRepository.findByLiker(account);
        if (likes == null) {
            return new HashSet<>();
        }
        likes.forEach(like -> likeIds.add(like.getPost().getId()));
        return likeIds;
    }
}
