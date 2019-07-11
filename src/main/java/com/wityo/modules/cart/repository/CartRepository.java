package com.wityo.modules.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.cart.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
