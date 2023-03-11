//package com.newsfeed.controller;
//
//import com.newsfeed.model.User;
//import com.newsfeed.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@Slf4j
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    private final UserService userService = new UserService();
//
//    @GetMapping(path = "/get")
//    public Optional<User> getPerson(@RequestParam final String userName) {
//        return Optional.of(userService.getUser(userName));
//    }
//
//}
