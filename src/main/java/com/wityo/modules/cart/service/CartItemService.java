package com.wityo.modules.cart.service;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.product.model.Product;

public interface CartItemService {
	
	public String addOrUpdateCart(Product product, String quantityOptions);
	public String deleteCartItemById(Long cartItemId);
	public String removeAllCartItems(Cart cart);
	public String reduceCartItem(Long productId, String quantityOption);
}
