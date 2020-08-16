package com.wityo.modules.user.service.impl;

import static java.lang.Boolean.TRUE;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.user.dto.RegistrationDTO;
import com.wityo.modules.user.exception.UsernameAlreadyExistsException;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;
import com.wityo.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> fetchAllUsers() {
		return null;
	}

	public User validateUser(String phoneNumber) {
		User user1 = userRepository.findByPhoneNumber(phoneNumber);
		return user1;
	}

	public User registerUser(RegistrationDTO registrationDTO) {
		try {
			User user = new User();
			Customer customer = new Customer();
			String userUUID = UUID.randomUUID().toString();
			userUUID = userUUID.replaceAll("-", "");
			user.setUserId(userUUID);
			user.setEnabled(TRUE);
			user.setPhoneNumber(registrationDTO.getPhoneNumber());
			user.setEmailId(registrationDTO.getEmailId());
			user.setCreatedAt(LocalDateTime.now());
			User tempUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
			if (tempUser == null) {
				customer.setFirstName(registrationDTO.getFirstName());
				customer.setLastName(registrationDTO.getFirstName());
				customer.setEmailId(registrationDTO.getEmailId());
				customer.setPhoneNumber(registrationDTO.getPhoneNumber());
				user.setCustomer(customer);
				customer.setUser(user);
				Cart cart = new Cart();
				cart.setCustomer(customer);
				customer.setCart(cart);
				return userRepository.save(user);
			} else {
				throw new UsernameAlreadyExistsException("This user already exists with phone number. Please login with that number");
			}
		} catch (Exception e) {
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
