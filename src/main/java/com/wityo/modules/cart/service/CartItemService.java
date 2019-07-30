package com.wityo.modules.cart.service;

import com.wityo.modules.cart.model.Cart;

public interface CartItemService {
	
	public String addOrUpdateCart(String productId, String quantityOptions);
	public String deleteCartItemById(Long cartItemId);
	public String removeAllCartItems(Cart cart);
	public String reduceCartItem(Long productId, String quantityOption);
}
