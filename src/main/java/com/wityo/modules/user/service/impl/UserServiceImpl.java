package com.wityo.modules.user.service.impl;

import static com.wityo.common.Constant.TWO_FACTOR_API_SECRET;
import static com.wityo.common.Constant.TWO_FACTOR_API_URL;
import static java.lang.Boolean.TRUE;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.google.gson.Gson;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.user.dto.OTPResponse;
import com.wityo.modules.user.dto.RegistrationDTO;
import com.wityo.modules.user.exception.UsernameAlreadyExistsException;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;
import com.wityo.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

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

	/*=================================GENERATE OTP================================================*/
	public OTPResponse generateOTP(String phoneNumber) {
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(TWO_FACTOR_API_URL + TWO_FACTOR_API_SECRET + "/SMS/+91" + phoneNumber + "/AUTOGEN", String.class);
			OTPResponse otpResponse = new Gson().fromJson(response.getBody(), OTPResponse.class);
			return otpResponse;
		} catch (HttpStatusCodeException e) {
			OTPResponse otpResponse = new Gson().fromJson(e.getResponseBodyAsString(), OTPResponse.class);
			return otpResponse;
		}
	}

	/*=================================VALIDATE OTP================================================*/
	public OTPResponse validateOTP(String sessionId, Long otp_input) {
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(TWO_FACTOR_API_URL + TWO_FACTOR_API_SECRET + "/SMS/VERIFY/" + sessionId + "/"+ otp_input, String.class);
			OTPResponse otpResponse = new Gson().fromJson(response.getBody(), OTPResponse.class);
			return otpResponse;
		} catch (HttpStatusCodeException e) {
			OTPResponse otpResponse = new Gson().fromJson(e.getResponseBodyAsString(), OTPResponse.class);
			return otpResponse;
		}
	}



}
