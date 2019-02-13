package com.dao;

import java.io.IOException;

import com.model.Cart;

public interface CartDao {

	Cart getCartByCartId(int CartId);
	
	Cart validate(int cartId) throws IOException;
	
	void update(Cart cart);
}
