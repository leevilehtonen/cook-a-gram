package com.leevilehtonen.cookagram.service;


import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.domain.ResourceLike;
import com.leevilehtonen.cookagram.repository.LikeRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public void toggleLike(Long target) {
        Account account = accountService.getAuthenticatedAccount();
        Post post = postRepository.findOne(target);
        ResourceLike like = likeRepository.findTopByLikerAndPost(account, post);
        if (like == null) {
            like = new ResourceLike();
            like.setLiker(account);
            like.setPost(post);
            likeRepository.save(like);
        } else {
            account.getLikes().remove(like);
            post.getLikes().remove(like);
            likeRepository.delete(like);
        }

    }

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
