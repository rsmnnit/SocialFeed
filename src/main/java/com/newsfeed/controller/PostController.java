package com.newsfeed.controller;

import com.newsfeed.exceptions.PostNotFoundException;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import com.newsfeed.service.PostService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping(path = "/event/like")
    public void likeEvent(@RequestParam @NonNull String eventId) {
        try {
            postService.likeEvent(eventId);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "/story/like")
    public void likePost(@RequestParam @NonNull String postId) {
        try {
            postService.likePost(postId);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /*
    Implement post Like and unlike flow
     */
}
