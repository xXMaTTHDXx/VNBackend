package io.matthd.core.database.mongo;

import com.mongodb.*;
import io.matthd.core.database.Database;

import java.util.Collections;

/**
 * Created by Matt on 2017-01-29.
 */
public class MongoDatabase implements Database {

    private DB db;
    private Mongo mongo;

    private String ip;
    private int port;

    public MongoDatabase(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void connect() {
        MongoCredential credential = MongoCredential.createCredential("user", "db", "pass".toCharArray());
        mongo = new MongoClient(new ServerAddress(ip, port), Collections.singletonList(credential));
        db = mongo.getDB("Velocity");
    }

    @Override
    public void disconnect() {
        mongo.close();
    }

    public DB getDb() {
        return db;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public DBCollection getCollection(String name) {
        return db.getCollection(name);
    }
}
