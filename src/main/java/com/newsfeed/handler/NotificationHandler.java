package com.newsfeed.handler;

import com.newsfeed.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationHandler {
    private final Map<String, UserNotificationWorker> notificationWorkers;
    private final UserRepository userRepository;

    public NotificationHandler() {
        notificationWorkers = new HashMap<>();
        userRepository = new UserRepository();
    }

    public void notifyFriendsOfUser(String userName) {
        final List<String> friendsByUser = userRepository.getFriendsByUser(userName);
        System.out.println("Friends of user : " + userName);
        friendsByUser.stream().forEach(System.out::println);
        for (String friend : friendsByUser) {
            if (!notificationWorkers.containsKey(friend)) {
                notificationWorkers.put(friend, new UserNotificationWorker(userName, friend));
            }
            new Thread(notificationWorkers.get(friend)).start();
        }
    }
}
