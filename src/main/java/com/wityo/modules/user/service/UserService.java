package com.wityo.modules.user.service;

import java.util.List;

import com.wityo.modules.user.model.User;

public interface UserService {
	public User registerUser(User user);
	public User validateUser(String phoneNumber);
	public List<User> fetchAllUsers();
	public String removeUser(String username);
	public User getUserByUsername(String username);

}
