package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Relationship;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import com.leevilehtonen.cookagram.repository.RelationshipRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    private Account a;

    @Before
    @Transactional
    public void setUp() throws Exception {
        a = new Account();
        a.setPassword("123456");
        a.setEmail("abc@cde.com");
        a.setFirstname("abc");
        a.setLastname("cde");
        a.setUsername("abcde");
    }


    @Test
    @Transactional
    public void save() throws Exception {
        accountService.save(a);
        Account b = accountRepository.findByUsername("abcde");
        assertNotNull(b);
        assertEquals(a, b);
        assertEquals(a.getEmail(), b.getEmail());
        assertEquals(a.getFirstname(), b.getFirstname());
        assertEquals(a.getLastname(), a.getLastname());
        assertEquals(a.getUsername(), a.getUsername());
    }

    @Test
    @Transactional
    public void getProfilePictureUrlWhenNoPosts() throws Exception {
        accountService.save(a);
        assertTrue(accountService.getProfilePictureUrl(a).contains("img"));
    }


    @Test
    @Transactional
    public void getFollowersNoFollowers() throws Exception {
        accountService.save(a);
        assertTrue(accountService.getFollowers(a).size() == 0);
    }

    @Test
    @Transactional
    public void getFollowersHasFollowers() throws Exception {
        accountService.save(a);
        Relationship relationship = new Relationship();
        relationship.setFollowed(accountRepository.findByUsername(a.getUsername()));
        relationship.setFollower(accountRepository.findByUsername("tester"));
        relationshipRepository.save(relationship);
        assertTrue(accountService.getFollowers(a).size() > 0);
    }

    @Test
    @Transactional
    public void getFollowingsNoFollowings() throws Exception {
        accountService.save(a);
        assertTrue(accountService.getFollowings(a).size() == 0);
    }

    @Test
    @Transactional
    public void getFollowersHasFollowings() throws Exception {
        accountService.save(a);
        Relationship relationship = new Relationship();
        relationship.setFollower(accountRepository.findByUsername(a.getUsername()));
        relationship.setFollowed(accountRepository.findByUsername("tester"));
        relationshipRepository.save(relationship);
        assertTrue(accountService.getFollowings(a).size() > 0);
    }

}