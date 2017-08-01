package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})


public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account a;

    @Before
    public void setUp() throws Exception {
        a = new Account();
        a.setPassword("123456");
        a.setEmail("abc@cde.com");
        a.setFirstname("abc");
        a.setLastname("cde");
        a.setUsername("abcde");
    }

    @Test
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
    public void getProfilePictureUrlWhenNoPosts() throws Exception {
        accountService.save(a);
        assertTrue(accountService.getProfilePictureUrl(a).contains("img"));
    }


    @Test
    public void getFollowersNoFollowers() throws Exception {
        accountService.save(a);
    }

    @Test
    public void getFollowings() throws Exception {
    }

}