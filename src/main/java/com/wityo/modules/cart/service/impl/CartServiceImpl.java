package com.wityo.modules.cart.service.impl;

import com.wityo.modules.cart.exception.InvalidCartException;
import com.wityo.modules.cart.exception.NoCartFoundException;
import com.wityo.modules.cart.exception.UnableToUpdateCartException;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.repository.CartRepository;
import com.wityo.modules.cart.service.CartService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart getCart(Long restaurantId) throws NoCartFoundException {
        User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerByPhoneNumber(userDetail.getPhoneNumber());
        Cart cart = customer.getCart();
        cart.setCartItems(cart.getCartItems().stream().filter(item -> item.getRestaurantId() == restaurantId).collect(Collectors.toSet()));
        return cart;
    }

    @Override
    public Cart validateCartById(Long cartId) throws InvalidCartException {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        }
        throw new InvalidCartException("This cart is not valid!");
    }

    @Override
    public Cart updateCart(Cart cart) throws UnableToUpdateCartException {
        return null;
    }

    public double getTotalCartAmount() {
        User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.getCustomerByPhoneNumber(userDetail.getPhoneNumber());
        Cart cart = customer.getCart();
        double grandTotal = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            grandTotal = grandTotal + cartItem.getPrice();
        }
        return grandTotal;
    }

}
