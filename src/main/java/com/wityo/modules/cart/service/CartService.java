package com.wityo.modules.cart.service;

import com.wityo.modules.cart.exception.InvalidCartException;
import com.wityo.modules.cart.exception.NoCartFoundException;
import com.wityo.modules.cart.exception.UnableToUpdateCartException;
import com.wityo.modules.cart.model.Cart;

public interface CartService {
    Cart getCart() throws NoCartFoundException;

    Cart validateCartById(Long cartId) throws InvalidCartException;

    Cart updateCart(Cart cart) throws UnableToUpdateCartException;
}
