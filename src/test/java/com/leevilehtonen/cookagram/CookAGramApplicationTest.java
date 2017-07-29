package com.leevilehtonen.cookagram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"production"})
public class CookAGramApplicationTest {

    @Test
    public void testProduction() {
        assertEquals("running", "running");
    }
}
