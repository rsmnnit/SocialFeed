package com.newsfeed;

public class UserFeedAlertSubscriber {

    public void sendNotification(String postOwnerUserName, String userName) {
        System.out.println("[User : " + userName + "] : There are new posts from friend: [" +  postOwnerUserName +
                "]," + " please refresh");
    }
}
