package com.newsfeed.service;

import com.newsfeed.exceptions.PostNotFoundException;
import com.newsfeed.handler.NotificationHandler;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.repository.PostRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Autowired
    private NotificationHandler notificationHandler;

    public PostServiceImpl() {
        this.postRepository = new PostRepository();
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
    public void likeEvent(String eventId) throws PostNotFoundException {
        postRepository.likeEvent(eventId);
    }

    @Override
    public void likePost(String postId) throws PostNotFoundException {
        postRepository.likePost(postId);
    }
}
