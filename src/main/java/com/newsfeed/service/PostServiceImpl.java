package com.newsfeed.service;

import com.newsfeed.handler.NotificationHandler;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.repository.PostRepository;
import lombok.NonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final NotificationHandler notificationHandler;

    public PostServiceImpl() {
        this.postRepository = new PostRepository();
        this.notificationHandler = new NotificationHandler();
    }

    @Override
    public String postStory(@NonNull Story storyPost) {
        final Story story = storyPost.toBuilder().creationTimeMillis(Instant.now().toEpochMilli()).build();
        final String postId = postRepository.postStory(story);
        notificationHandler.notifyFriendsOfUser(storyPost.getPostOwnerUserName());
        return postId;
    }

    @Override
    public String postEvent(@NonNull Event eventPost) {
        final Event event = eventPost.toBuilder().creationTimeMillis(Instant.now().toEpochMilli()).build();
        final String eventId = postRepository.postEvent(event);
        notificationHandler.notifyFriendsOfUser(eventPost.getEventOwnerUserName());
        return eventId;
    }

    @Override
    public List<Story> fetchPostsById(@NonNull List<Long> postIds) {
        return new ArrayList<>();
    }
}
