package com.wityo.modules.user.service;

import java.util.List;

import com.wityo.modules.user.model.Customer;

public interface CustomerService {
	
	public Customer addCustomer(Customer customer);
	public List<Customer> fetchAllCustomer();
	public Customer getCustomerByEmailId(String emailId);
	public Customer getCustomerByPhoneNumber(String phoneNumber);

}
