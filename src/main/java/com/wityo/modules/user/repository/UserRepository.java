package com.wityo.modules.user.repository;

import com.wityo.modules.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    User findByPhoneNumber(String phoneNumber);

    User findByCustomerCustomerId(Long customerId);

}
