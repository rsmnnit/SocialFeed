package com.newsfeed.service;

import com.newsfeed.model.Feed;
import com.newsfeed.model.Story;
import com.newsfeed.repository.PostRepository;
import com.newsfeed.repository.UserRepository;
import lombok.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public FeedService() {
        this.postRepository = new PostRepository();
        this.userRepository = new UserRepository();
    }

    public Feed getFeedForUser(@NonNull String userName) {
        final List<String> friends = userRepository.getFriendsByUser(userName);
        final List<String> topics = userRepository.getTopicsByUser(userName);
        final List<Story> storiesByFriends = postRepository.fetchStoriesByUsers(friends, 50);
        final List<Story> storiesByTopics = postRepository.fetchStoriesByTopics(topics, 50);
        return Feed.builder()
                .stories(Stream.concat(storiesByTopics.stream(), storiesByFriends.stream()).distinct()
                        .sorted(Comparator.comparing(Story::getCreationTimeMillis).reversed()).collect(Collectors.toList()))
                .events(postRepository.fetchMostHappeningEvent(5))
                .build();
    }
}
