package com.wityo.modules.cart.service;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.UserCartItem;
import com.wityo.modules.product.model.Product;

public interface CartItemService {

    String addItemFromMenu(UserCartItem userCartItem);

    String deleteCartItemById(Long cartItemId);

    String removeAllCartItems(Cart cart);

    String subtractCartItem(Long cartItemId);

    String subtractItemFromMenu(UserCartItem userCartItem);

    String addItemFromCart(Long cartItemId);
}
