package com.newsfeed.controller;

import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.service.PostService;
import com.newsfeed.service.PostServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService = new PostServiceImpl();

    @GetMapping(path = "/story")
    public void postStory(@RequestBody @NonNull final Story story) {
        postService.postStory(story);
    }

    @GetMapping(path = "/event")
    public void postEvent(@RequestBody @NonNull final Event event) {
        postService.postEvent(event);
    }
}
