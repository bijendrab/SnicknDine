package com.service;

import com.model.Cart;
import com.model.CartItem;

public interface CartItemService {

	void addCartItem(CartItem cartItem);
	void removeCartItem(int CartItemId);
	void removeAllCartItems(Cart cart);
}
