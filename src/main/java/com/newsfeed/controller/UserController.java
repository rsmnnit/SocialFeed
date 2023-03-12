package com.newsfeed.controller;

import com.newsfeed.model.User;
import com.newsfeed.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping(path = "/details/{userName}")
    public Optional<User> getPerson(@PathVariable @NonNull final String userName) {
        return Optional.of(userService.getUser(userName));
    }

    @PostMapping(path = "/register")
    public void registerUser(@RequestBody @NonNull final User user) {
        userService.registerUser(user);
    }

    @PostMapping(path = "/add-friend/{userName1}/{userName2}")
    public void makeFriends(@PathVariable @NonNull String userName1, @PathVariable @NonNull String userName2) {
        userService.makeFriends(userName1, userName2);
    }

    @PostMapping(path = "/add-friend/{userName1}/{topic}")
    public void followTopic(@PathVariable @NonNull String userName, @PathVariable @NonNull String topic) {
        userService.followTopic(userName, topic);
    }

    @GetMapping(path = "/friends/{userName}")
    public List<String> getFriendsByUser(@PathVariable @NonNull String userName) {
        return userService.getFriends(userName);
    }

    @GetMapping(path = "/topics/{userName}")
    public List<String> getTopicsByUser(@PathVariable @NonNull String userName) {
        return userService.getTopicsByUserName(userName);
    }
}
