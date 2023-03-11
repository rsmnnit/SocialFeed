package com.newsfeed.service;

import com.newsfeed.model.User;
import com.newsfeed.repository.UserRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void registerUser(@NonNull User user) {
        userRepository.registerUser(user.toBuilder().userName(user.getUserName().toLowerCase()).build());
        logger.info("Registered User: " + user);
    }

    public User getUser(@NonNull String userName) {
        return userRepository.getUser(userName);
    }

    public void makeFriends(@NonNull String userName1, @NonNull String userName2) {
        userRepository.makeFriends(userName1, userName2);
        logger.info("Friends added, User1 : " + userName1 + " and user2: " + userName2);
    }

    public void followTopic(@NonNull String userName, @NonNull String topic) {
        userRepository.followTopic(userName, topic);
        logger.info("Following topic, User1 : " + userName + " , Topic: " + topic);
    }
}
