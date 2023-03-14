package com.newsfeed.service;

import com.newsfeed.handler.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.Instant;
import java.util.List;

public class NotificationService {
    @Autowired
    private NotificationHandler notificationHandler;
    private static final Long timeoutSecond = 30L;

    public DeferredResult<String> getNotification(String userName) {
        DeferredResult<String> output = new DeferredResult<>(timeoutSecond * 1000);
        output.onTimeout(() -> output.setErrorResult("No new notifications."));
        final long startEpochSecond = Instant.now().getEpochSecond();
        new Thread(() -> {
            do {
                final List<String> notifiers = notificationHandler.getUserToNotifierUserMap().remove(userName);
                if (notifiers != null && !notifiers.isEmpty()) {
                    output.setResult("There are new posts from users: " + String.join(", ", notifiers));
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
