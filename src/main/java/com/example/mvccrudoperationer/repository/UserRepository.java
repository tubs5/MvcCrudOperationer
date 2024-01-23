package com.example.mvccrudoperationer.repository;

import com.example.mvccrudoperationer.model.UserEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tobias Heidlund
 */
public class UserRepository extends BaseRepository{

    public UserRepository() {
        super();
        String createUserTable ="CREATE TABLE `users` (\n" +
                "  `id` int(16) NOT NULL AUTO_INCREMENT,\n" +
                "  `username` varchar(32) NOT NULL UNIQUE,\n" +
                "  `password` varchar(32) NOT NULL,\n" +
                "   PRIMARY KEY (`id`)\n" +
                ");";
        try {
            connection.prepareCall(createUserTable).execute();
            addUser("adam", "adam");
            addUser("david", "david");
            addUser("nora", "nora");
            addUser("tobias", "tobias");
        } catch (SQLException e) {
            if(!e.getLocalizedMessage().contains("already exists")) throw new RuntimeException(e);
        }
    }

    private void addUser(String username,String password) throws SQLException {
        PreparedStatement sql = connection.prepareCall("INSERT INTO users(USERNAME, PASSWORD) values (?,?)");
        sql.setString(1,username);
        sql.setString(2,password);
        sql.execute();
    }

    public UserEntry get(String username, String password) {
        try {
            PreparedStatement ps = connection.prepareCall("SELECT id FROM users WHERE username=? AND password=?");
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new UserEntry(username,rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
