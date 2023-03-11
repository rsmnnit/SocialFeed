package com.newsfeed.service;

import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import lombok.NonNull;

import java.util.List;

public interface PostService {
    String postStory(@NonNull Story storyPost);

    String postEvent(@NonNull Event eventPost);

    List<Story> fetchPostsById(@NonNull List<Long> postIds);
}
