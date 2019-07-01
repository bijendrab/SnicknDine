package com.wityo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wityo.modules.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			return userRepo.findByUsername(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("No user found with this username");
		}
	}
	
	public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
		try {
			return userRepo.findByUserId(userId);
		} catch (Exception e) {
			throw new UsernameNotFoundException("No user found with this username");
		}
	}

}
