package com.newsfeed.handler;

import com.newsfeed.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationHandler {
    private final Map<String, UserNotificationWorker> notificationWorkers;
    @Getter
    private final Map<String, List<String>> userToNotifierUserMap;
    @Autowired
    private UserRepository userRepository;

    public NotificationHandler() {
        notificationWorkers = new HashMap<>();
        userToNotifierUserMap = new ConcurrentHashMap<>();
    }

    public void notifyFriendsOfUser(String userName) {
        final List<String> friendsByUser = userRepository.getFriendsByUser(userName);
        for (String friend : friendsByUser) {
            if (!notificationWorkers.containsKey(friend)) {
                notificationWorkers.put(friend, new UserNotificationWorker(userName, friend));
            }
            System.out.println("Sending notification to user " + friend);
            userToNotifierUserMap.putIfAbsent(friend, new ArrayList<>());
            userToNotifierUserMap.get(friend).add(userName);
            System.out.println("notification in notification handler: " + userToNotifierUserMap.size());
            new Thread(notificationWorkers.get(friend)).start();
        }
    }
}
