package com.wityo.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.user.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	public Customer findByEmailId(String emailId);
	public Customer findByPhoneNumber(String phoneNumber);

}
