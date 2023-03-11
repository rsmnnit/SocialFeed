package com.newsfeed.connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DbConnection {
    private static MongoDatabase database;

    private DbConnection() {

    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            MongoClient mongo = MongoClients.create("mongodb://localhost:27017");
            database = mongo.getDatabase("news-feed");
        }
        return database;
    }
}
