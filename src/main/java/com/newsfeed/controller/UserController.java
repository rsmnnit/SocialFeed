package com.newsfeed.controller;

import com.newsfeed.exceptions.UserAlreadyExistsException;
import com.newsfeed.model.User;
import com.newsfeed.model.UserFriend;
import com.newsfeed.model.UserTopic;
import com.newsfeed.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/details")
    public Optional<User> getPerson(@RequestParam @NonNull final String userName) {
        return Optional.of(userService.getUser(userName));
    }

    @PostMapping(path = "/register")
    public void registerUser(@RequestBody @NonNull final User user) {
        try {
            userService.registerUser(user);
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(path = "/add-friend")
    public void makeFriends(@RequestBody @NonNull UserFriend userFriend) {
        userService.makeFriends(userFriend);
    }

    @PostMapping(path = "/follow-topic")
    public void followTopic(@RequestBody @NonNull UserTopic userTopic) {
        userService.followTopic(userTopic);
    }

    @GetMapping(path = "/friends")
    public List<String> getFriendsByUser(@RequestParam @NonNull String userName) {
        return userService.getFriends(userName);
    }

    @GetMapping(path = "/topics")
    public List<String> getTopicsByUser(@RequestParam @NonNull String userName) {
        return userService.getTopicsByUserName(userName);
    }
}
