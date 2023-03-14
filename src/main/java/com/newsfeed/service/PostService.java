package com.newsfeed.service;

import com.newsfeed.exceptions.PostNotFoundException;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import lombok.NonNull;

public interface PostService {
    String postStory(@NonNull Story storyPost);

    String postEvent(@NonNull Event eventPost);

    void likeEvent(String eventId) throws PostNotFoundException;

    void likePost(String postId) throws PostNotFoundException;
}
