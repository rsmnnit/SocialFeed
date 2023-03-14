package com.newsfeed.config;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.newsfeed.handler.NotificationHandler;
import com.newsfeed.repository.PostRepository;
import com.newsfeed.repository.UserRepository;
import com.newsfeed.service.FeedService;
import com.newsfeed.service.NotificationService;
import com.newsfeed.service.PostService;
import com.newsfeed.service.PostServiceImpl;
import com.newsfeed.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AppConfig {
    @Bean
    public NotificationHandler notificationHandler() {
        return new NotificationHandler();
    }

    @Bean
    public PostService postService() {
        return new PostServiceImpl();
    }

    @Bean
    public NotificationService notificationService() {
        return new NotificationService();
    }

    @Bean
    public FeedService feedService() {
        return new FeedService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }
    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        return MongoClients.create("mongodb://localhost:27017").getDatabase("news-feed");
    }

}
