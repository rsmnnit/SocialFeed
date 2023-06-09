package com.newsfeed.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.newsfeed.exceptions.UserAlreadyExistsException;
import com.newsfeed.model.User;
import com.newsfeed.model.UserFriend;
import com.newsfeed.model.UserTopic;
import lombok.NonNull;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.in;

public class UserRepository {
    @Autowired
    private MongoDatabase database;
    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository() {
        this.gson = new Gson();
    }

    public void registerUser(@NonNull User user) throws UserAlreadyExistsException {
        MongoCursor<Document> users = database.getCollection("users").find(in("userName", user.getUserName())).iterator();
        if (users.hasNext()) {
            logger.error("User already exists");
            throw new UserAlreadyExistsException("User already exists");
        }
        Document dbObject = Document.parse(gson.toJson(user));
        database.getCollection("users").insertOne(dbObject);
    }

    public User getUser(@NonNull String userName) {
        MongoCursor<Document> users = database.getCollection("users").find(in("userName", userName)).iterator();
        if (!users.hasNext()) {
            logger.error("User not exists");
            throw new RuntimeException("User not exists");
        }
        final Document next = users.next();
        User user = (new Gson()).fromJson(next.toJson(), User.class);
        user.setUserId(next.get("_id").toString());
        return user;
    }

    public void makeFriends(@NonNull UserFriend userFriend) {
        MongoCursor<Document> users =
                database.getCollection("friends").find(and(in("userName", userFriend.getUserName()), in("friendUserName",
                        userFriend.getFriendUserName()))).iterator();
        if (!users.hasNext()) {
            Document dbObject =
                    Document.parse(gson.toJson(UserFriend.builder().friendUserName(userFriend.getUserName())
                            .userName(userFriend.getFriendUserName()).build()));
            Document dbObject2 =
                    Document.parse(gson.toJson(UserFriend.builder().friendUserName(userFriend.getUserName())
                            .userName(userFriend.getFriendUserName()).build()));
            database.getCollection("friends").insertMany(Arrays.asList(dbObject2, dbObject));
        }
    }

    public void followTopic(@NonNull UserTopic userTopic) {
        MongoCursor<Document> users =
                database.getCollection("topics").find(and(in("userName", userTopic.getUserName()), in("topic", userTopic.getTopic()))).iterator();
        if (!users.hasNext()) {
            Document dbObject =
                    Document.parse(gson.toJson(UserTopic.builder().userName(userTopic.getUserName()).topic(userTopic.getTopic()).build()));
            database.getCollection("topics").insertOne(dbObject);
        }
    }

    public List<String> getFriendsByUser(@NonNull String userName) {
        final MongoCursor<Document> friendsItr = database.getCollection("friends").find(in("userName", userName)).iterator();
        List<String> friends = new ArrayList<>();
        while (friendsItr.hasNext()) {
            final Document next = friendsItr.next();
            UserFriend userFriend = (new Gson()).fromJson(next.toJson(), UserFriend.class);
            friends.add(userFriend.getFriendUserName());
        }
        return friends;
    }

    public List<String> getTopicsByUser(String userName) {
        final MongoCursor<Document> topicsItr = database.getCollection("topics").find(in("userName", userName)).iterator();
        List<String> topics = new ArrayList<>();
        while (topicsItr.hasNext()) {
            final Document next = topicsItr.next();
            UserTopic userTopic = (new Gson()).fromJson(next.toJson(), UserTopic.class);
            topics.add(userTopic.getTopic());
        }
        return topics;
    }
}
