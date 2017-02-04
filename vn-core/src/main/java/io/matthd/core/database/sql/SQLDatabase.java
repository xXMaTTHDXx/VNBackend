package io.matthd.core.database.sql;

import io.matthd.core.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Matt on 2017-01-29.
 */
public class SQLDatabase implements Database {

    private String host, port, db, user, pass;

    private Connection connection;

    public SQLDatabase(String host, String port, String db, String user, String pass) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }


    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, pass);
            Statement statement = connection.createStatement();

            statement.addBatch("CREATE TABLE IF NOT EXISTS users (Uuid VARCHAR(36) NOT NULL PRIMARY KEY, Coins INT NOT NULL, Achievements VARCHAR(128), Rank VARCHAR(16), Statistics VARCHAR(128));");
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
