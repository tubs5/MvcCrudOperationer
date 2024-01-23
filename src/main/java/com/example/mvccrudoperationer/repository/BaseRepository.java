package com.example.mvccrudoperationer.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tobias Heidlund
 */
public abstract class BaseRepository {
    protected Connection connection;

    public BaseRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connect();
        } catch (SQLException e) {
            if (e.getLocalizedMessage().equals("Unknown database 'invoicedb'")) {
                createDatabase();
            }
            else {
                throw new RuntimeException(e);
            }

        }
    }

    private void createDatabase() {
        try {
           Connection tempConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
           tempConnection.prepareCall("Create database invoicedb").execute();
           tempConnection.close();
           connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoiceDB", "root", "");
    }
}
