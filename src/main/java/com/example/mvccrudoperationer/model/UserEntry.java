package com.example.mvccrudoperationer.model;

import java.io.Serializable;

/**
 * @author Tobias Heidlund
 */
public class UserEntry implements Serializable {
    private final String username;
    private final int id;

    public UserEntry(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
