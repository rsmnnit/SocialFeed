package com.newsfeed.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.newsfeed.connection.DbConnection;
import com.newsfeed.model.User;
import com.newsfeed.model.UserFriend;
import com.newsfeed.model.UserTopic;
import lombok.NonNull;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.in;

public class UserRepository {
    MongoCollection<Document> usersCollection;
    MongoCollection<Document> topicCollection;
    MongoCollection<Document> friendsCollection;
    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository() {
        MongoDatabase database = DbConnection.getDatabase();
        this.usersCollection = database.getCollection("users");
        this.friendsCollection = database.getCollection("friends");
        this.topicCollection = database.getCollection("topics");
        this.gson = new Gson();
    }

    public void registerUser(@NonNull User user) {
        MongoCursor<Document> users = usersCollection.find(in("userName", user.getUserName())).iterator();
        if (users.hasNext()) {
            logger.error("User already exists");
            return;
//            throw new RuntimeException("User already exists");
        }
        Document dbObject = Document.parse(gson.toJson(user));
        usersCollection.insertOne(dbObject);
    }

    public User getUser(@NonNull String userName) {
        MongoCursor<Document> users = usersCollection.find(in("userName", userName)).iterator();
        if (!users.hasNext()) {
            logger.error("User not exists");
            return User.builder().build();
//            throw new RuntimeException("User not exists");
        }
        final Document next = users.next();
        User user = (new Gson()).fromJson(JSON.serialize(next), User.class);
        user.setUserId(next.get("_id").toString());
        return user;
    }

    public void makeFriends(@NonNull String userName1, @NonNull String userName2) {
        MongoCursor<Document> users =
                friendsCollection.find(and(in("userName", userName1), in("userName", userName2))).iterator();
        if (!users.hasNext()) {
            Document dbObject =
                    Document.parse(gson.toJson(UserFriend.builder().friendUserName(userName2).userName(userName1).build()));
            Document dbObject2 =
                    Document.parse(gson.toJson(UserFriend.builder().friendUserName(userName1).userName(userName2).build()));
            friendsCollection.insertMany(Arrays.asList(dbObject2, dbObject));
        }
    }

    public void followTopic(@NonNull String userName, @NonNull String topic) {
        MongoCursor<Document> users =
                friendsCollection.find(and(in("userName", userName), in("topic", topic))).iterator();
        if (!users.hasNext()) {
            Document dbObject =
                    Document.parse(gson.toJson(UserTopic.builder().userId(userName).topic(topic).build()));
            topicCollection.insertMany(Arrays.asList(dbObject, dbObject));
        }
    }

    public List<String> getFriendsByUser(@NonNull String userName) {
        final MongoCursor<Document> friendsItr = friendsCollection.find(in("userName", userName)).iterator();
        List<String> friends = new ArrayList<>();
        while (friendsItr.hasNext()) {
            final Document next = friendsItr.next();
            UserFriend userFriend = (new Gson()).fromJson(JSON.serialize(next), UserFriend.class);
            friends.add(userFriend.getFriendUserName());
        }
        return friends;
    }

    public List<String> getTopicsByUser(String userName) {
        final MongoCursor<Document> topicsItr = topicCollection.find(in("userName", userName)).iterator();
        List<String> topics = new ArrayList<>();
        while (topicsItr.hasNext()) {
            final Document next = topicsItr.next();
            UserTopic userTopic = (new Gson()).fromJson(JSON.serialize(next), UserTopic.class);
            topics.add(userTopic.getTopic());
        }
        return topics;
    }
}
