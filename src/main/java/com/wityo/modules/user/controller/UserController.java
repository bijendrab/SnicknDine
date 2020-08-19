package com.wityo.modules.user.controller;

import com.wityo.common.Constant;
import com.wityo.common.ResponseCreator;
import com.wityo.common.WityoRestAppProperties;
import com.wityo.modules.user.dto.LoginRequestDTO;
import com.wityo.modules.user.dto.OTPResponse;
import com.wityo.modules.user.dto.RegistrationDTO;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.CustomerRepository;
import com.wityo.modules.user.repository.UserRepository;
import com.wityo.modules.user.service.UserService;
import com.wityo.security.config.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userServiceImpl;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private WityoRestAppProperties wityoRestAppProperties;

	@ApiOperation(value = "generate otp", response = OTPResponse.class)
	@GetMapping(value = "/generateOTP/{phoneNumber}")
	public ResponseEntity<?> generateOTP(@PathVariable String phoneNumber) {
		Map<String, Object> response = new HashMap<String, Object>();
		OTPResponse otpGenerationStatus = userServiceImpl.generateOTP(phoneNumber);
		if(otpGenerationStatus == null || otpGenerationStatus.getStatus().equals("Error")){
			response.put("message", "could not generate OTP");
			response.put("body", otpGenerationStatus);
			response.put("error", Boolean.TRUE);
			response.put("status", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("message", "generated OTP successfully");
		response.put("body", userServiceImpl.generateOTP(phoneNumber));
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.FOUND);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "validate otp", response = OTPResponse.class)
	@GetMapping(value = "/validateOTP/{sessionId}/{otpInput}")
	public ResponseEntity<?> validateOTP(@PathVariable String sessionId,@PathVariable Long otpInput) {
		Map<String, Object> response = new HashMap<String, Object>();
		OTPResponse otpValidationStatus = userServiceImpl.validateOTP(sessionId,otpInput);
		if(otpValidationStatus == null || otpValidationStatus.getStatus().equals("Error")){
			response.put("message", "could not validate OTP");
			response.put("body", otpValidationStatus);
			response.put("error", Boolean.TRUE);
			response.put("status", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		response.put("message", "validate OTP successfully");
		response.put("body", userServiceImpl.validateOTP(sessionId,otpInput));
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.FOUND);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/validate")
	public ResponseEntity<?> checkUserExists(@RequestParam("phoneNumber") String phoneNumber) {
		User user = userServiceImpl.validateUser(phoneNumber);
		Map<String, Object> response = new HashMap<String, Object>();
		if (user == null) {
			response = ResponseCreator.errorResponseCreator("PHONE_DOESNT_EXIST", null, true,
					HttpStatus.EXPECTATION_FAILED);
		} else {
			response = ResponseCreator.successResponseCreator("PHONE_NO_EXISTS", null, false, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/validate")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO dto) {
		User result = userServiceImpl.validateUser(dto.getPhoneNumber());
		Map<String, Object> response = new HashMap<String, Object>();
		if (result != null) {
			response = ResponseCreator.successResponseCreator(
					"User is present in record, user validated and JWT assigned.", generateTokenForUser(result), false,
					HttpStatus.OK);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} else {
			response = ResponseCreator.successResponseCreator("User not found in record, redirect to registration page",
					null, true, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {
		User registeredUser = userServiceImpl.registerUser(registrationDTO);
		Map<String, Object> response = new HashMap<String, Object>();
		if (registeredUser != null) {
			response = ResponseCreator.successResponseCreator("User is registere and JWT is assigned.",
					generateTokenForUser(registeredUser), false, HttpStatus.FOUND);
		} else {
			response = ResponseCreator.successResponseCreator("Unable to register user. Try again after sometime", null,
					true, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	private String generateTokenForUser(User user) {
		String jwt = Constant.TOKEN_PREFIX + tokenProvider.generateJwtToken(user);
		return jwt;
	}

	@GetMapping("/getUserDetails")
	public ResponseEntity<Customer> getUser() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = customerRepository.findByPhoneNumber(user.getPhoneNumber());
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

}
