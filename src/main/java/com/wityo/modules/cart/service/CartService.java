package com.wityo.modules.cart.service;

import java.util.List;
import java.util.Map;

import com.wityo.modules.cart.exception.InvalidCartException;
import com.wityo.modules.cart.exception.NoCartFoundException;
import com.wityo.modules.cart.exception.UnableToUpdateCartException;
import com.wityo.modules.cart.model.Cart;

public interface CartService {
	public Cart getCartById(Long cartId) throws NoCartFoundException;
	public Cart validateCartById(Long cartId) throws InvalidCartException;
	public List<Map<String, Object>> updateCart(Cart cart) throws UnableToUpdateCartException;
}
