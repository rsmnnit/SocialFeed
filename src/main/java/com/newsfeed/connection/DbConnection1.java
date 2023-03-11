package com.newsfeed.connection;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.newsfeed.model.Story;
import org.bson.Document;

import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

public class DbConnection1 {

    public static void main(String args[]) {

        // Creating a Mongo client
        MongoClient mongo = MongoClients.create("mongodb://localhost:27017");

        // Creating Credentials
//        MongoCredential credential;
//        credential = MongoCredential.createCredential("sampleUser", "myDb",
//                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("news-feed-test");
        System.out.println(database.getName());
        MongoCollection<Document> collection = database.getCollection("posts");
        System.out.println("Collection myCollection selected successfully");

//        Document document1 = new Document("title", "MongoDB")
//                .append("description", "database")
//                .append("likes", 100)
//                .append("url", "http://www.tutorialspoint.com/mongodb/")
//                .append("by", "tutorials point");
//        Document document2 = new Document("title", "RethinkDB")
//                .append("description", "database")
//                .append("likes", 200)
//                .append("url", "http://www.tutorialspoint.com/rethinkdb/")
//                .append("by", "tutorials point");
//        List<Document> list = new ArrayList<>();
//        list.add(document1);
//        list.add(document2);
//        collection.insertMany(list);
        for (String name : database.listCollectionNames()) {
            System.out.println(name);
        }
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
        System.out.println("Document inserted successfully");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("title", "MongoDB");
        BasicDBObject fields = new BasicDBObject();
        fields.put("description", 1);

        FindIterable<Document> doc = collection.find(eq("title", "MongoDB"))
                .sort(Sorts.descending("likes"));
        it = doc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
        FindIterable<Document> doc1 = collection.find(in("title", Arrays.asList("MongoDB", "RethinkDB", "xyz")))
                .sort(Sorts.descending("likes"));
        it = doc1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }

        Story story = Story.builder()
                .content("Story content")
                .creationTimeMillis(Instant.now().toEpochMilli())
                .postOwnerUserName("radhe")
                .latitude(12.34)
                .longitude(23.56)
                .topics(Arrays.asList("A", "B"))
                .build();

        Gson gson = new Gson();
        Document dbObject = Document.parse(gson.toJson(story));
        collection.insertOne(dbObject);
        collection.updateOne(Filters.eq("_id", dbObject.get("_id")), Updates.set("likes", "1"));
        if (dbObject.containsKey("likes"))
            collection.updateOne(Filters.eq("_id", dbObject.get("_id")), new BasicDBObject().append("likes", 50));
        FindIterable<Document> doc2 = collection.find(in("postOwnerUserName", Arrays.asList("radhe")))
                .sort(Sorts.descending("content"));
        it = doc2.iterator();
        while (it.hasNext()) {
            final Document next = (Document) it.next();

            System.out.println(next.toJson());
            Story st = (new Gson()).fromJson(next.toJson(), Story.class);
            st.setStoryId(next.get("_id").toString());
            System.out.println(st);
            i++;
        }
    }
}
