package com.leevilehtonen.cookagram.validator;

import com.leevilehtonen.cookagram.domain.Account;
import com.leevilehtonen.cookagram.domain.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class AccountUniqueValidatorTest {

    @Autowired
    private AccountUniqueValidator accountUniqueValidator;

    private Account a;

    @Before
    public void setUp() {
        a = new Account();
        a.setPassword("123456");
        a.setEmail("abc@cde.com");
        a.setFirstname("abc");
        a.setLastname("cde");
        a.setUsername("abcde");
    }

    @Test
    public void validateWithValidAccount() {
        Errors errors = new MapBindingResult(new HashMap<>(), "errors");
        accountUniqueValidator.validate(a, errors);
        assertEquals(0, errors.getErrorCount());
    }


    @Test
    public void validateWithInvalidEmail() {
        a.setEmail("test@test.com");
        Errors errors = new MapBindingResult(new HashMap<>(), "errors");
        accountUniqueValidator.validate(a, errors);
        assertEquals(1, errors.getErrorCount());
    }


    @Test
    public void validateWithInvalidUsername() {
        a.setUsername("tester");
        Errors errors = new MapBindingResult(new HashMap<>(), "errors");
        accountUniqueValidator.validate(a, errors);
        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void supportsAccount() {
        assertTrue(accountUniqueValidator.supports(Account.class));
    }

    @Test
    public void supportOnlyAccount() {
        assertFalse(accountUniqueValidator.supports(Post.class));
    }

}