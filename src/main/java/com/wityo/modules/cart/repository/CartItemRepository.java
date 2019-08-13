package com.wityo.modules.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wityo.modules.cart.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	@Query(value = "DELETE from cart_item where cart_id=?1", nativeQuery = true)
	public void deleteCartItemsByCartId(Long cartId);

}
