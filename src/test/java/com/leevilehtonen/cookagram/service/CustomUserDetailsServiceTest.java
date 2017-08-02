package com.leevilehtonen.cookagram.service;

import com.leevilehtonen.cookagram.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AccountService service;

    private Account a;

    @Before
    public void setUp() throws Exception {
        a = new Account();
        a.setPassword("123456");
        a.setEmail("abc@cde.com");
        a.setFirstname("abc");
        a.setLastname("cde");
        a.setUsername("abcde");
        service.save(a);
    }

    @Test
    @Transactional
    public void loadUserByUsername() throws Exception {
        UserDetails user = customUserDetailsService.loadUserByUsername(a.getUsername());
        assertEquals(a.getUsername(), user.getUsername());
        assertEquals(1, user.getAuthorities().size());
    }

}