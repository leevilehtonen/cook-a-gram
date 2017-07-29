package com.leevilehtonen.cookagram;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"development"})
public class CookAGramApplicationTestDevelopment {

    @Test
    public void testDefault() {
        assertEquals("f", "f");
    }
}
