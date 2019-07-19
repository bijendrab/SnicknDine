package com.wityo.modules.cart.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wityo.modules.cart.exception.InvalidCartException;
import com.wityo.modules.cart.exception.NoCartFoundException;
import com.wityo.modules.cart.exception.UnableToUpdateCartException;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Override
	public Cart getCartById(Long cartId) throws NoCartFoundException {
		return null;
	}

	@Override
	public Cart validateCartById(Long cartId) throws InvalidCartException {
		return null;
	}

	@Override
	public List<Map<String, Object>> updateCart(Cart cart) throws UnableToUpdateCartException {
		return null;
	}

}
