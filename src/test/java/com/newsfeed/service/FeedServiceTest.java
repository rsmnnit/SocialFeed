package com.newsfeed.service;

import com.newsfeed.model.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedServiceTest {

    private final static Long SIX_HOURS = 6*60*60*1000L;
    @BeforeEach
    void setUp() {
        Story story1 = Story.builder().creationTimeMillis(Instant.now().toEpochMilli()).likes(12).build();
        Story story2 =
                Story.builder().creationTimeMillis(Instant.now().toEpochMilli() - 2 * SIX_HOURS).likes(25).build();
        Story story3 = Story.builder().creationTimeMillis(Instant.now().toEpochMilli() - SIX_HOURS/2).likes(15).build();
        Story story4 = Story.builder().creationTimeMillis(Instant.now().toEpochMilli()).likes(10).build();
//        List<Story> stories = ;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFeedForUser() {
    }
}