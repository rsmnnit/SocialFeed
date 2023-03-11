package com.newsfeed.connection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DbConnection {
    private static MongoDatabase database;

    private DbConnection() {

    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            MongoClient mongo = new MongoClient("localhost", 27017);
            database = mongo.getDatabase("news-feed");
        }
        return database;
    }
}
