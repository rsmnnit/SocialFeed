package com.newsfeed;

import com.newsfeed.model.Feed;
import com.newsfeed.model.User;
import com.newsfeed.service.FeedService;
import com.newsfeed.service.PostService;
import com.newsfeed.service.PostServiceImpl;
import com.newsfeed.service.UserService;

public class Main {
    public static void main(String[] args) {
        PostService postService = new PostServiceImpl();
        FeedService feedService = new FeedService();
        UserService userService = new UserService();
        DummyPostPublishService dummyPostPublishService = new DummyPostPublishService();
        User user1 = User.builder().userName("radhe").firstName("Radhe").lastName("Lodhi").build();
        User user2 = User.builder().userName("raj").firstName("Raj").lastName("B").build();
        User user3 = User.builder().userName("akshay").firstName("Akshay").lastName("A").build();
        User user4 = User.builder().userName("ram").firstName("Ram").lastName("R").build();
        User user5 = User.builder().userName("ajay").firstName("Ajay").lastName("A").build();
        userService.registerUser(user1);
        userService.registerUser(user2);
        userService.registerUser(user3);
        userService.registerUser(user4);
        userService.registerUser(user5);
        userService.makeFriends(user1.getUserName(), user2.getUserName());
        userService.makeFriends(user1.getUserName(), user3.getUserName());
        userService.makeFriends(user2.getUserName(), user4.getUserName());
        userService.makeFriends(user3.getUserName(), user5.getUserName());
//        dummyPostPublishService.postDummyStory(user1.getUserName(), 5);
//        dummyPostPublishService.postDummyStory(user1.getUserName(), 3, Arrays.asList("Covid"));
//        dummyPostPublishService.postDummyStory(user2.getUserName(), 6);
//        dummyPostPublishService.postDummyStory(user3.getUserName(), 4);
//        dummyPostPublishService.postDummyStory(user3.getUserName(), 4, Arrays.asList("Covid", "Traffic"));
//        dummyPostPublishService.postDummyStory(user4.getUserName(), 3);
//        dummyPostPublishService.postDummyStory(user5.getUserName(), 7);
        final Feed user1Feed = feedService.getFeedForUser(user1.getUserName());
        final Feed user2Feed = feedService.getFeedForUser(user2.getUserName());
        final Feed user3Feed = feedService.getFeedForUser(user3.getUserName());
        final Feed user4Feed = feedService.getFeedForUser(user4.getUserName());
        final Feed user5Feed = feedService.getFeedForUser(user5.getUserName());
        printFeed(user1Feed, user1.getUserName());
        printFeed(user2Feed, user2.getUserName());
        printFeed(user3Feed, user3.getUserName());
        printFeed(user4Feed, user4.getUserName());
        printFeed(user5Feed, user5.getUserName());
    }

    private static void printFeed(Feed feed, String userName) {
        System.out.println("[Feed For User: " + userName + "]");
        feed.getStories().forEach(System.out::println);
    }
}