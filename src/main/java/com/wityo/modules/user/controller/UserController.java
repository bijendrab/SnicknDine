package com.wityo.modules.user.controller;

import com.wityo.common.Constant;
import com.wityo.common.ResponseCreator;
import com.wityo.modules.user.dto.LoginRequestDTO;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.service.UserService;
import com.wityo.security.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    RestTemplate restTemplate;


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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = userServiceImpl.registerUser(user);
        Map<String, Object> response = new HashMap<String, Object>();
        if (registeredUser != null) {
            response = ResponseCreator.successResponseCreator(
                    "User is registere and JWT is assigned.", generateTokenForUser(registeredUser), false,
                    HttpStatus.FOUND);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        } else {
            response = ResponseCreator.successResponseCreator("Unable to register user. Try again after sometime",
                    null, true, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/getMenu")
    public String getMenu() {
        String result = restTemplate.getForObject
                ("http://localhost:8080/api/restaurant/getMenu", String.class);
        return result;

    }

    private String generateTokenForUser(User user) {
        String jwt = Constant.TOKEN_PREFIX + tokenProvider.generateJwtToken(user);
        return jwt;
    }

}
