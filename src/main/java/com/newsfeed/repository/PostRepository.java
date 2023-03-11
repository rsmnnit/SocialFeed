package com.newsfeed.repository;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.util.JSON;
import com.newsfeed.connection.DbConnection;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import lombok.NonNull;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.ne;

public class PostRepository {
    private final MongoCollection<Document> eventsCollection;
    private final MongoCollection<Document> storiesCollection;
    private final Gson gson;

    public PostRepository() {
        MongoDatabase database = DbConnection.getDatabase();
        this.eventsCollection = database.getCollection("events");
        this.storiesCollection = database.getCollection("posts");
        this.gson = new Gson();
    }

    public String postStory(@NonNull Story story) {
        Document dbObject = Document.parse(gson.toJson(story));
        storiesCollection.insertOne(dbObject);
        return dbObject.get("_id").toString();
    }

    public String postEvent(@NonNull Event event) {
        Document dbObject = Document.parse(gson.toJson(event));
        eventsCollection.insertOne(dbObject);
        return dbObject.get("_id").toString();
    }

    public List<Story> fetchStoriesByUsers(@NonNull List<String> userNames, Integer limit) {
        FindIterable<Document> storyDocs = storiesCollection.find(in("postOwnerUserName", userNames))
                .sort(Sorts.descending("creationTimeMillis")).limit(limit);
        final MongoCursor<Document> cursor = storyDocs.iterator();
        List<Story> stories = new ArrayList<>();
        while (cursor.hasNext()) {
            final Document next = cursor.next();
            Story story = (new Gson()).fromJson(JSON.serialize(next), Story.class);
            story.setStoryId(next.get("_id").toString());
            stories.add(story);
        }
        return stories;
    }

    public List<Event> fetchEventsByUsers(@NonNull List<String> userNames, Integer limit) {
        FindIterable<Document> eventDocs = storiesCollection.find(in("eventOwnerUserName", userNames))
                .sort(Sorts.descending("creationTimeMillis")).limit(limit);
        final MongoCursor<Document> cursor = eventDocs.iterator();
        List<Event> events = new ArrayList<>();
        while (cursor.hasNext()) {
            final Document next = cursor.next();
            Event event = (new Gson()).fromJson(JSON.serialize(next), Event.class);
            event.setEventId(next.get("_id").toString());
            events.add(event);
        }
        return events;
    }

    public List<Event> fetchMostHappeningEvent(Integer limit) {
        FindIterable<Document> storyDocs = storiesCollection.find()
                .sort(Sorts.descending("likes")).limit(limit);
        final MongoCursor<Document> cursor = storyDocs.iterator();
        List<Event> events = new ArrayList<>();
        while (cursor.hasNext()) {
            final Document next = cursor.next();
            Event event = (new Gson()).fromJson(JSON.serialize(next), Event.class);
            events.add(event);
        }
        return events;
    }

    public List<Story> fetchStoriesByTopics(@NonNull List<String> topics, Integer limit) {
        FindIterable<Document> storyDocs = storiesCollection.find(in("topics", topics))
                .sort(Sorts.descending("creationTimeMillis")).limit(limit);
        final MongoCursor<Document> cursor = storyDocs.iterator();
        List<Story> stories = new ArrayList<>();
        while (cursor.hasNext()) {
            Story story = (new Gson()).fromJson(JSON.serialize(cursor.next()), Story.class);
            stories.add(story);
        }
        return stories;
    }
    // TODO- method to update/add likes.
}
