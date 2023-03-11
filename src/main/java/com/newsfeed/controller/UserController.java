package com.newsfeed.controller;

import com.newsfeed.model.User;
import com.newsfeed.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService = new UserService();

    @GetMapping(path = "/details/{userName}")
    public Optional<User> getPerson(@PathVariable final String userName) {
        return Optional.of(userService.getUser(userName));
    }

    @PostMapping(path = "/register")
    public void registerUser(@RequestBody final User user) {
        userService.registerUser(user);
    }
}
