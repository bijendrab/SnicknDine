package com.wityo.modules.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.common.Constant;
import com.wityo.common.ResponseCreator;
import com.wityo.modules.user.dto.LoginRequestDTO;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.service.UserService;
import com.wityo.security.config.JwtTokenProvider;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userServiceImpl;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/validate")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO dto) {
		User result = userServiceImpl.validateUser(Long.parseLong(dto.getPhoneNumber()));
		Map<String, Object> response = new HashMap<String, Object>();
		if (result != null) {
			response = ResponseCreator.successResponseCreator(
					"User is present in record, user validated and JWT assigned.", generateTokenForUser(result), false,
					HttpStatus.FOUND);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.FOUND);
		} else {
			response = ResponseCreator.successResponseCreator("User not found in record, redirect to registration page",
					null, true, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		User registeredUser = userServiceImpl.registerUser(user);
		Map<String, Object> response = new HashMap<String, Object>();
		if (registeredUser != null) {
			response = ResponseCreator.successResponseCreator(
					"User is registere and JWT is assigned.", generateTokenForUser(registeredUser), false,
					HttpStatus.FOUND);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
		} else {
			response = ResponseCreator.successResponseCreator("Unable to register user. Try again after sometime",
					null, true, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	private String generateTokenForUser(User user) {
		String jwt = Constant.TOKEN_PREFIX + tokenProvider.generateJwtToken(user);
		return jwt;
	}

}
