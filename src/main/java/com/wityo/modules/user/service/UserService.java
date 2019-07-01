package com.wityo.modules.user.service;

import java.util.List;

import com.wityo.common.exception.WityoGenericException;
import com.wityo.modules.user.model.User;

public interface UserService {
	
	public List<User> fetchAllUsers();
	public User saveUser(User user) throws WityoGenericException;
	public String removeUser(String username);
	public User getUserByUsername(String username);

}
