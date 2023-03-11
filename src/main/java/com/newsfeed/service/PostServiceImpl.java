package com.newsfeed.service;

import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.repository.PostRepository;
import lombok.NonNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl() {
        this.postRepository = new PostRepository();
    }

    @Override
    public String postStory(@NonNull Story storyPost) {
        final Story story = storyPost.toBuilder().creationTimeMillis(Instant.now().toEpochMilli()).build();
        return postRepository.postStory(story);
    }

    @Override
    public String postEvent(@NonNull Event eventPost) {
        final Event event = eventPost.toBuilder().creationTimeMillis(Instant.now().toEpochMilli()).build();
        return postRepository.postEvent(event);
    }

    @Override
    public List<Story> fetchPostsById(@NonNull List<Long> postIds) {
        return new ArrayList<>();
    }
}
