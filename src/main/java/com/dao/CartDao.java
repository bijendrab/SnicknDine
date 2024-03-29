package com.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.model.Cart;

public interface CartDao {

	Cart getCartByCartId(int CartId);
	
	Cart validate(int cartId) throws IOException;

	List<Map<String, Object>> update(Cart cart);
}
