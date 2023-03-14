package com.newsfeed.controller;

import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.service.PostService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping(path = "/story")
    public String postStory(@RequestBody @NonNull final Story story) {
        return postService.postStory(story);
    }

    @PostMapping(path = "/event")
    public String postEvent(@RequestBody @NonNull final Event event) {
        return postService.postEvent(event);
    }

    /*
    Implement post Like and unlike flow
     */
}
