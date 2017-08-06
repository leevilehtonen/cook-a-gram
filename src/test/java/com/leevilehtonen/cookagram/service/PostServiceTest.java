package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.PostRepository;
import com.leevilehtonen.cookagram.repository.TagRepository;
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
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RelationshipService relationshipService;


    @Autowired
    private TagRepository tagRepository;

    private Post p;
    private Account a;

    @Before
    @Transactional
    public void setUp() throws Exception {
        a = accountRepository.findByUsername("tester");
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        String file = "abc,defghiklmnopqr";
        String tags = "test";
        postService.createPost(file, tags, a);
        assertEquals(2, postRepository.findAllByOrderByDateDesc().size());
        assertNotNull(tagRepository.findByName("test"));
        assertEquals(2, accountRepository.findByUsername("tester").getPosts().size());
    }

    @Test
    @Transactional
    public void saveAndLoadTags() throws Exception {
        String tags = "test, hello";
        assertEquals(0, tagRepository.count());
        assertEquals(2, postService.saveAndLoadTags(tags).size());
    }

    @Test
    @Transactional
    public void getPostsByAccount() throws Exception {
        Post p = new Post();
        p.setPoster(a);
        p.setLikes(new HashSet<>());
        p = postRepository.save(p);

        assertEquals(p, postService.getPostsByAccount(a).get(0)[0]);
        assertEquals(1, postService.getPostsByAccount(a).size());
    }

    @Test
    public void getMostLikedPost() throws Exception {
        Post p = new Post();
        p.setPoster(a);
        p.setLikes(new HashSet<>());
        p = postRepository.save(p);

        assertEquals(p, postService.getMostLikedPost().get(0));
    }

}