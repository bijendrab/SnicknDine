package com.wityo.modules.user.repository;

import com.wityo.modules.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmailId(String emailId);

    Customer findByPhoneNumber(String phoneNumber);

}
