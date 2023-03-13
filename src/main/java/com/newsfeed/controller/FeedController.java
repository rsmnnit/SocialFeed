package com.newsfeed.controller;

import com.newsfeed.model.Feed;
import com.newsfeed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;

    @GetMapping(path = "")
    public Feed getFeed(@RequestParam final String userName) {
        return feedService.getFeedForUser(userName);
    }
}
