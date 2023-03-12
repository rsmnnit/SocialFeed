package com.newsfeed.service;

import com.newsfeed.handler.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Instant;

public class NotificationService {
    @Autowired
    private NotificationHandler notificationHandler;

    public DeferredResult<String> getNotification(String userName) {
        long timeoutSecond = 30L;
        DeferredResult<String> output = new DeferredResult<>(timeoutSecond * 1000);
        output.onTimeout(() -> output.setErrorResult("No new notifications."));
        final long startEpochSecond = Instant.now().getEpochSecond();
        new Thread(() -> {
            do {
//                System.out.println("Fetching notification for user: " + userName);
                final String notifier = notificationHandler.getUserToNotifierUserMap().remove(userName);
                if (notifier != null) {
                    output.setResult("There are new posts from user: " + notifier);
                    break;
                }
                try {
                    // Check every 200 milliseconds.
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (startEpochSecond + timeoutSecond > Instant.now().getEpochSecond());
        }).start();
        return output;
    }
}
