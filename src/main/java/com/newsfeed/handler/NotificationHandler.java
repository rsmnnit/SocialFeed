package com.newsfeed.handler;

import java.util.HashMap;
import java.util.Map;

public class NotificationHandler {
    private final Map<String, UserNotificationWorker> notificationWorkers;

    public NotificationHandler() {
        notificationWorkers = new HashMap<>();
    }

    public void notifyUser() {

    }
}
