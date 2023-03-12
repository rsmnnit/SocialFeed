package com.newsfeed.config;

import com.newsfeed.handler.NotificationHandler;
import com.newsfeed.service.NotificationService;
import com.newsfeed.service.PostService;
import com.newsfeed.service.PostServiceImpl;
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

}
