package com.newsfeed.controller;

import com.newsfeed.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private ExecutorService bakers = Executors.newFixedThreadPool(5);
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/")
    public DeferredResult<String> publisher(@RequestParam String userName) {
        // Long polling till 30 seconds.
//        DeferredResult<String> output = new DeferredResult<>(30000L);
//        output.onTimeout(() -> output.setErrorResult("No new notifications."));
//        bakers.execute(() -> {
//            try {
//                notificationService.getNotificationForUser(userName, output);
//            } catch (Exception e) {
//                output.setErrorResult("Something went wrong!");
//            }
//        });
//        return output;
        return notificationService.getNotification(userName);
    }
}
