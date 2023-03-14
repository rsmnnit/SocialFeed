package com.newsfeed.handler;

import com.newsfeed.repository.UserRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationHandler {
    private final Map<String, UserNotificationWorker> notificationWorkers;
    @Getter
    private final Map<String, List<String>> userToNotifierUserMap;
    private final UserRepository userRepository;

    public NotificationHandler() {
        notificationWorkers = new HashMap<>();
        userRepository = new UserRepository();
        userToNotifierUserMap = new ConcurrentHashMap<>();
    }

    public void notifyFriendsOfUser(String userName) {
        final List<String> friendsByUser = userRepository.getFriendsByUser(userName);
//        System.out.println("Friends of user : " + userName);
//        friendsByUser.stream().forEach(System.out::println);
        for (String friend : friendsByUser) {
            if (!notificationWorkers.containsKey(friend)) {
                notificationWorkers.put(friend, new UserNotificationWorker(userName, friend));
            }
            userToNotifierUserMap.getOrDefault(friend, new ArrayList<>()).add(userName);
            new Thread(notificationWorkers.get(friend)).start();
        }
    }
}
