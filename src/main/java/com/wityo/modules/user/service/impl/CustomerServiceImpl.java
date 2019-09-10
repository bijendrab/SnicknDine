package com.wityo.modules.user.service.impl;

import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.repository.CustomerRepository;
import com.wityo.modules.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepo;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    @Override
    public List<Customer> fetchAllCustomer() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getCustomerByEmailId(String emailId) {
        return customerRepo.findByEmailId(emailId);
    }

    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepo.findByPhoneNumber(phoneNumber);
    }

}
