package com.wityo.modules.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wityo.modules.user.dto.RegistrationDTO;
import com.wityo.modules.user.exception.UsernameAlreadyExistsException;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;
import com.wityo.modules.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public List<User> fetchAllUsers() {
		return null;
	}

	@Override
	public User saveUser(RegistrationDTO newUser) {
		try {
			User user = new User();
			Customer customer = new Customer();
			user.setEnabled(false);
			user.setEmailId(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			
			User tempUser = userRepository.findByUsername(user.getUsername());
			
			if(tempUser == null) {
				customer.setFirstName(newUser.getFirstName());
				customer.setLastName(newUser.getLastName());
				customer.setPhoneNumber(newUser.getPhoneNumber());
				user.setCustomer(customer);
				user.setPassword(encoder.encode(user.getPassword()));
				return userRepository.save(user);
			} else {
				throw new UsernameAlreadyExistsException("This username already exists, try with some other username");
			}
		}catch (Exception e) {
			if(e.getClass().equals(UsernameAlreadyExistsException.class)) {
				throw new UsernameAlreadyExistsException(e.getMessage());
			} else {
				throw new RuntimeException("Server not responding, please try again later");
			}
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
