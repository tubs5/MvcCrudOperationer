package com.example.mvccrudoperationer.repository;

import com.example.mvccrudoperationer.model.UserEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tobias Heidlund
 */
public class UserRepository extends BaseRepository{

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
