package com.newsfeed.service;

import com.newsfeed.exceptions.UserAlreadyExistsException;
import com.newsfeed.model.User;
import com.newsfeed.model.UserFriend;
import com.newsfeed.model.UserTopic;
import com.newsfeed.repository.UserRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerUser(@NonNull User user) throws UserAlreadyExistsException {
        userRepository.registerUser(user.toBuilder().userName(user.getUserName().toLowerCase()).build());
        logger.info("Registered User: " + user);
    }

    public User getUser(@NonNull String userName) {
        return userRepository.getUser(userName);
    }

    public void makeFriends(@NonNull UserFriend userFriend) {
        userRepository.makeFriends(userFriend);
        logger.info("Friends added, User1 : " + userFriend.getUserName() + " and user2: " + userFriend.getFriendUserName());
    }

    public void followTopic(@NonNull UserTopic userTopic) {
        userRepository.followTopic(userTopic);
        logger.info("Following topic, User1 : " + userTopic.getUserName() + " , Topic: " + userTopic.getTopic());
    }

    public List<String> getFriends(String userName) {
        return userRepository.getFriendsByUser(userName);
    }

    public List<String> getTopicsByUserName(String userName) {
        return userRepository.getTopicsByUser(userName);
    }
}
