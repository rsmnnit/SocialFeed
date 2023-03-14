package com.newsfeed.service;

import com.newsfeed.model.Feed;
import com.newsfeed.model.Story;
import com.newsfeed.repository.PostRepository;
import com.newsfeed.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    private static final Integer POST_LIMIT = 10;
    private static final Integer EVENT_LIMIT = 5;
    private static final Long SIX_HOURS = 6 * 60 * 60 * 1000L;

    public Feed getFeedForUser(@NonNull String userName) {
        final List<String> friends = userRepository.getFriendsByUser(userName);
        final List<String> topics = userRepository.getTopicsByUser(userName);
        final List<Story> storiesByFriends = postRepository.fetchStoriesByUsers(friends, POST_LIMIT);
        final List<Story> storiesByTopics = postRepository.fetchStoriesByTopics(topics, POST_LIMIT);
        return Feed.builder()
                .stories(Stream.concat(storiesByTopics.stream(), storiesByFriends.stream()).distinct()
                        .sorted((story1, story2) -> {
                            // If 2 stories are within 6 hours window, sort using likes.
                            if (Math.abs(story2.getCreationTimeMillis() - story1.getCreationTimeMillis()) < SIX_HOURS) {
                                return story2.getLikes() - story1.getLikes();
                            }
                            return Math.toIntExact(story2.getCreationTimeMillis() - story1.getCreationTimeMillis());
                        }).limit(POST_LIMIT).collect(Collectors.toList()))
                .events(postRepository.fetchMostHappeningEvent(EVENT_LIMIT).stream().sorted((event1, event2) -> {
                    // If 2 events are within 6 hours window, sort using likes.
                    if (Math.abs(event2.getCreationTimeMillis() - event1.getCreationTimeMillis()) < SIX_HOURS) {
                        return event2.getLikes() - event1.getLikes();
                    }
                    return Math.toIntExact(event2.getCreationTimeMillis() - event1.getCreationTimeMillis());
                }).limit(EVENT_LIMIT).collect(Collectors.toList()))
                .build();
    }
}
