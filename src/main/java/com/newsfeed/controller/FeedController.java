package com.newsfeed.controller;

import com.newsfeed.model.Feed;
import com.newsfeed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {
    FeedService feedService = new FeedService();
    @GetMapping(path = "/{userName}")
    public Feed getFeed(@PathVariable final String userName) {
        return feedService.getFeedForUser(userName);
    }
}
