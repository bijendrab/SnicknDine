package com.service;

import com.model.Cart;

import java.util.List;
import java.util.Map;

public interface CartService {

	Cart getCartByCartId(int CartId);
	Cart validate(int CartId);
	List<Map<String, Object>> update(Cart cart);
}
