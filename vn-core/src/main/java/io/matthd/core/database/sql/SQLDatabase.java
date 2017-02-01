package io.matthd.core.database.sql;

import io.matthd.core.database.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
