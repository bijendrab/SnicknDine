package com.wityo.modules.user.service;

import com.wityo.modules.user.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    List<Customer> fetchAllCustomer();

    Customer getCustomerByEmailId(String emailId);

    Customer getCustomerByPhoneNumber(String phoneNumber);

}
