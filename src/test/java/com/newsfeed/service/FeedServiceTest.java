package com.newsfeed.service;

import com.newsfeed.model.Event;
import com.newsfeed.model.Feed;
import com.newsfeed.model.Story;
import com.newsfeed.repository.PostRepository;
import com.newsfeed.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FeedServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FeedService feedService;

    private final static Long SIX_HOURS = 6 * 60 * 60 * 1000L;
    private Story story1, story2, story3, story4;
    private Event event1, event2, event3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        story1 =
                Story.builder().postOwnerUserName("user1").content("content1").creationTimeMillis(Instant.now().toEpochMilli()).likes(12).build();
        story2 =
                Story.builder().postOwnerUserName("user2").content("content1").creationTimeMillis(Instant.now().toEpochMilli() - 2 * SIX_HOURS).likes(25).build();
        story3 =
                Story.builder().postOwnerUserName("user3").content("content1").creationTimeMillis(Instant.now().toEpochMilli() - SIX_HOURS / 2).likes(15).build();
        story4 =
                Story.builder().postOwnerUserName("user3").content("content1").creationTimeMillis(Instant.now().toEpochMilli())
                        .topics(Arrays.asList("BitCoin")).likes(10).build();
        event1 =
                Event.builder().eventOwnerUserName("user3")
                        .eventName("even1")
                        .eventDate(Date.valueOf("2023-03-26"))
                        .eventDescription("description1")
                        .venue("Venue1")
                        .venueLatitude(12.36)
                        .venueLongitude(45.63)
                        .creationTimeMillis(Instant.now().toEpochMilli() - 2 * SIX_HOURS).likes(25).build();
        event2 =
                Event.builder().eventOwnerUserName("user2").eventName("even1")
                        .eventDate(Date.valueOf("2023-03-26"))
                        .eventDescription("description1")
                        .venue("Venue1")
                        .venueLatitude(12.36)
                        .venueLongitude(45.63).creationTimeMillis(Instant.now().toEpochMilli() - SIX_HOURS / 2)
                        .likes(12).build();
        event3 =
                Event.builder().eventOwnerUserName("user1").eventName("even1")
                        .eventDate(Date.valueOf("2023-03-26"))
                        .eventDescription("description1")
                        .venue("Venue1")
                        .venueLatitude(12.36)
                        .venueLongitude(45.63).creationTimeMillis(Instant.now().toEpochMilli())
                        .likes(10).build();

        Mockito.when(userRepository.getFriendsByUser("user5")).thenReturn(Arrays.asList("user1", "user2", "user3"));
        Mockito.when(userRepository.getTopicsByUser("user5")).thenReturn(Arrays.asList("Covid", "BitCoin"));
        Mockito.when(postRepository.fetchStoriesByUsers(Arrays.asList("user1", "user2", "user3"), FeedService.POST_LIMIT))
                .thenReturn(Arrays.asList(story1, story2, story3, story4));
        Mockito.when(postRepository.fetchStoriesByTopics(Arrays.asList("Covid", "BitCoin"), FeedService.POST_LIMIT))
                .thenReturn(Arrays.asList(story4));

        Mockito.when(postRepository.fetchMostHappeningEvent(FeedService.EVENT_LIMIT))
                .thenReturn(Arrays.asList(event1, event2, event3));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFeedForUser_shouldReturnOrderedFeed() {
        final Feed feed = feedService.getFeedForUser("user5");
        assertEquals(feed.getStories().size(), 4);
        assertEquals(feed.getEvents().size(), 3);
        assertEquals(feed.getStories().get(0), story3);
        assertEquals(feed.getStories().get(1), story1);
        assertEquals(feed.getStories().get(2), story4);
        assertEquals(feed.getStories().get(3), story2);

        assertEquals(feed.getEvents().get(0), event2);
        assertEquals(feed.getEvents().get(1), event3);
        assertEquals(feed.getEvents().get(2), event1);
    }
}