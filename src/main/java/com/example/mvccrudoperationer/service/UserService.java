package com.example.mvccrudoperationer.service;

import com.example.mvccrudoperationer.model.UserEntry;
import com.example.mvccrudoperationer.repository.UserRepository;

/**
 * @author Tobias Heidlund
 */
public class UserService {
    private final UserRepository userRepository = new UserRepository();
    public UserEntry get(String username, String password){
        return userRepository.get(username,password);
    }

}
