package com.wityo.modules.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;
import com.wityo.modules.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> fetchAllUsers() {
		return null;
	}
	
	public User validateUser(Long phoneNumber) {
		try {
			User user1 = userRepository.findByPhoneNumber(phoneNumber);
			if(null != user1) {
				return user1;
			} else {
				throw new Exception();
			}
		}catch (Exception e) {
			throw new RuntimeException("User not found in the records, needs to be registered!");
		}
	}

	public User registerUser(User user) {
		try {
			User user1 = userRepository.save(user);
			if(null != user1) {
				return user1;
			} else {
				throw new RuntimeException();
			}
		}catch (Exception e) {
			throw new RuntimeException("Unable to register user, please try again");
		}
	}
	
	@Override
	public String removeUser(String username) {
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		return null;
	}

}
