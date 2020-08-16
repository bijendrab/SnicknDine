package com.wityo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;	
	}
	
	public User loadUserByUserId(String userId) throws UsernameNotFoundException {
		try {
			return userRepo.findByUserId(userId);
		} catch (Exception e) {
			throw new UsernameNotFoundException("No user found with this user id");
		}
	}
	
	public User loadUserByPhoneNumber(String phoneNumber) {
		try {
			return userRepo.findByPhoneNumber(phoneNumber);
		} catch (Exception e) {
			throw new UsernameNotFoundException("No user found with this phone number");
		}
	}

}
