package com.newsfeed;

import com.newsfeed.model.User;

public class UserFeedAlertSubscriber {
    private final User user;
    public UserFeedAlertSubscriber(User user) {
        this.user = user;
    }

    public void sendNotification() {
        System.out.println("[User : " + user.getUserName() + "] : There are new posts, please refresh");
    }
}
