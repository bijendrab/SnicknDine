package com.wityo.modules.user.service;

import com.wityo.modules.user.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);

    User validateUser(String phoneNumber);

    List<User> fetchAllUsers();

    String removeUser(String username);

    User getUserByUsername(String username);

}
