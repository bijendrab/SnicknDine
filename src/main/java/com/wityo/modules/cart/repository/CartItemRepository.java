package com.wityo.modules.cart.repository;

import com.wityo.modules.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE from cart_item where cart_id=?1", nativeQuery = true)
    void deleteCartItemsByCartId(Long cartId);

}
