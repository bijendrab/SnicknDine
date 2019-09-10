package com.wityo.modules.cart.service;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.product.model.Product;

public interface CartItemService {

    String addOrUpdateCart(Product product, String quantityOptions);

    String deleteCartItemById(Long cartItemId);

    String removeAllCartItems(Cart cart);

    String reduceCartItem(String productId, String quantityOption);
}
