package com.newsfeed;

import com.google.gson.Gson;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.service.PostService;
import com.newsfeed.service.PostServiceImpl;

import java.time.Instant;
import java.util.List;

public class DummyPostPublishService {
    private final Gson gson;
    private final PostService postService;

    public DummyPostPublishService() {
        postService = new PostServiceImpl();
        gson = new Gson();
    }

    public void postDummyEvent(String userName, Integer count) {
        for (int i = 0; i < count; i++) {
            Event event = Event.builder()
                    .creationTimeMillis(Instant.now().toEpochMilli())
                    .eventOwnerUserName(userName)
                    .eventDescription("Dummy Event Description from user : " + userName + " at time: " + Instant.now().toEpochMilli())
                    .venue("Forum Mall")
                    .venueLongitude(15.46)
                    .venueLatitude(17.56)
                    .locationLatitude(12.34)
                    .locationLongitude(23.56)
                    .likes(5)
                    .build();
            postService.postEvent(event);
        }
    }

    public void postDummyStory(String userName, Integer count, List<String> topics) {
        for (int i = 0; i < count; i++) {
            Story story = Story.builder()
                    .content("Dummy Story from User " + userName + " at time: " + Instant.now().toEpochMilli())
                    .creationTimeMillis(Instant.now().toEpochMilli())
                    .postOwnerUserName(userName)
                    .latitude(12.34)
                    .longitude(23.56)
                    .topics(topics)
                    .likes(5)
                    .build();
            postService.postStory(story);
        }
    }

    public void postDummyStory(String userName, Integer count) {
        for (int i = 0; i < count; i++) {
            Story story = Story.builder()
                    .content("Dummy Story from User " + userName + " at time: " + Instant.now().toEpochMilli())
                    .creationTimeMillis(Instant.now().toEpochMilli())
                    .postOwnerUserName(userName)
                    .latitude(14.26)
                    .longitude(27.56)
                    .likes(3)
                    .build();
            postService.postStory(story);
        }
    }
}
