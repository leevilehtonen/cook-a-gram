package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    private Post p;
    private Account a;

    @Before
    @Transactional
    public void setUp() throws Exception {
        a = accountRepository.findByUsername("tester");
        p = new Post();
        p.setPoster(a);
        p.setLikes(new HashSet<>());
        p = postRepository.save(p);
    }

    @Test
    @Transactional
    public void toggleLike() throws Exception {
        assertEquals(0, p.getLikes().size());
        likeService.toggleLike(a, p.getId());
        assertEquals(1, postRepository.findOne(p.getId()).getLikes().size());
        likeService.toggleLike(a, p.getId());
        assertEquals(0, postRepository.findOne(p.getId()).getLikes().size());
    }

    @Test
    @Transactional
    public void getAccountLikedPostIdsNoPosts() throws Exception {
        assertEquals(0, likeService.getAccountLikedPostIds(a).size());
    }

    @Test
    @Transactional
    public void getAccountLikedPostIdsOnePost() throws Exception {
        likeService.toggleLike(a, p.getId());
        assertEquals(1, likeService.getAccountLikedPostIds(a).size());
        assertTrue(likeService.getAccountLikedPostIds(a).contains(p.getId()));
    }

}