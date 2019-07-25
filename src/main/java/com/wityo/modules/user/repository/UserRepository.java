package com.wityo.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByUserId(Long userId);
	public User findByPhoneNumber(Long phoneNumber);

}
