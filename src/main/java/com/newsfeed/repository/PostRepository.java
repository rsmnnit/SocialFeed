package com.newsfeed.repository;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.newsfeed.exceptions.PostNotFoundException;
import com.newsfeed.model.Event;
import com.newsfeed.model.Story;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;

public class PostRepository {
    private final Gson gson;
    @Autowired
    private MongoDatabase database;

    public PostRepository() {
        this.gson = new Gson();
    }

    public String postStory(@NonNull Story story) {
        Document dbObject = Document.parse(gson.toJson(story));
        database.getCollection("posts").insertOne(dbObject);
        return dbObject.get("_id").toString();
    }

    public String postEvent(@NonNull Event event) {
        Document dbObject = Document.parse(gson.toJson(event));
        database.getCollection("events").insertOne(dbObject);
        return dbObject.get("_id").toString();
    }

    public void likeEvent(String eventId) throws PostNotFoundException {
        MongoCursor<Document> events = database.getCollection("events").find(in("_id", new ObjectId(eventId))).iterator();
        if (!events.hasNext()) {
            throw new PostNotFoundException("Event with id: " + eventId + " not exists");
        }
        database.getCollection("events").findOneAndUpdate(in("_id", new ObjectId(eventId)), Updates.inc("likes", 1));
    }

    public void likePost(String postId) throws PostNotFoundException {
        MongoCursor<Document> storyDocs = database.getCollection("posts").find(in("_id", new ObjectId(postId))).iterator();
        if (!storyDocs.hasNext()) {
            throw new PostNotFoundException("Post with id: " + postId + " not exists");
        }
        database.getCollection("posts").findOneAndUpdate(in("_id", new ObjectId(postId)), Updates.inc("likes", 1));
    }

    public List<Story> fetchStoriesByUsers(@NonNull List<String> userNames, Integer limit) {
        FindIterable<Document> storyDocs = database.getCollection("posts").find(in("postOwnerUserName", userNames))
                .sort(Sorts.descending("creationTimeMillis")).limit(limit);
        final MongoCursor<Document> cursor = storyDocs.iterator();
        List<Story> stories = new ArrayList<>();
        while (cursor.hasNext()) {
            final Document next = cursor.next();
            Story story = (new Gson()).fromJson(next.toJson(), Story.class);
            story.setStoryId(next.get("_id").toString());
            stories.add(story);
        }
        return stories;
    }

    public List<Event> fetchEventsByUsers(@NonNull List<String> userNames, Integer limit) {
        FindIterable<Document> eventDocs = database.getCollection("events").find(in("eventOwnerUserName", userNames))
                .sort(Sorts.descending("creationTimeMillis")).limit(limit);
        final MongoCursor<Document> cursor = eventDocs.iterator();
        List<Event> events = new ArrayList<>();
        while (cursor.hasNext()) {
            final Document next = cursor.next();
            Event event = (new Gson()).fromJson(next.toJson(), Event.class);
            event.setEventId(next.get("_id").toString());
            events.add(event);
        }
        return events;
    }

    public List<Event> fetchMostHappeningEvent(Integer limit) {
        FindIterable<Document> storyDocs = database.getCollection("events").find()
                .sort(Sorts.descending("likes")).limit(limit);
        final MongoCursor<Document> cursor = storyDocs.iterator();
        List<Event> events = new ArrayList<>();
        while (cursor.hasNext()) {
            final Document next = cursor.next();
            Event event = (new Gson()).fromJson(next.toJson(), Event.class);
            event.setEventId(next.get("_id").toString());
            events.add(event);
        }
        return events;
    }

    public List<Story> fetchStoriesByTopics(@NonNull List<String> topics, Integer limit) {
        FindIterable<Document> storyDocs = database.getCollection("posts").find(in("topics", topics))
                .sort(Sorts.descending("creationTimeMillis")).limit(limit);
        final MongoCursor<Document> cursor = storyDocs.iterator();
        List<Story> stories = new ArrayList<>();
        while (cursor.hasNext()) {
            Story story = (new Gson()).fromJson(cursor.next().toJson(), Story.class);
            stories.add(story);
        }
        return stories;
    }
}
