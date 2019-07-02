package com.wityo.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.common.Constant;
import com.wityo.modules.user.dto.LoginRequestDTO;
import com.wityo.modules.user.dto.RegistrationDTO;
import com.wityo.modules.user.service.UserService;
import com.wityo.security.config.JwtTokenProvider;
import com.wityo.security.dto.JwtSuccessDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userServiceImpl;

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO user, BindingResult result) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = Constant.TOKEN_PREFIX + tokenProvider.generateJwtToken(auth);
		return ResponseEntity.ok(new JwtSuccessDto(true, jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO customer){
		userServiceImpl.saveUser(customer);
		Map<String,String> response = new HashMap<>();
		response.put("Message", "New user registered successfully");
		return ResponseEntity.accepted().body(response);
	}


}
