package com.newsfeed.handler;

import com.newsfeed.UserFeedAlertSubscriber;

public class UserNotificationWorker implements Runnable {
    private final String postOwnerUserName;
    private final String userToNotify;
    private final UserFeedAlertSubscriber userFeedAlertSubscriber;

    public UserNotificationWorker(String postOwnerUserName, String userToNotify) {
        this.postOwnerUserName = postOwnerUserName;
        this.userToNotify = userToNotify;
        this.userFeedAlertSubscriber = new UserFeedAlertSubscriber();
    }

    @Override
    public void run() {
        userFeedAlertSubscriber.sendNotification(postOwnerUserName, userToNotify);
    }
}
