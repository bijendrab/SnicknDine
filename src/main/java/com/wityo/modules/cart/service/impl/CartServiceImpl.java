package com.wityo.modules.cart.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityo.modules.cart.exception.InvalidCartException;
import com.wityo.modules.cart.exception.NoCartFoundException;
import com.wityo.modules.cart.exception.UnableToUpdateCartException;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.repository.CartRepository;
import com.wityo.modules.cart.service.CartService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.service.CustomerService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public Cart getCart() throws NoCartFoundException {
		User userDetail = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = customerService.getCustomerByPhoneNumber(userDetail.getPhoneNumber());
		Cart cart = customer.getCart();
		return cart;
	}

	@Override
	public Cart validateCartById(Long cartId) throws InvalidCartException {
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
		if(optionalCart.isPresent()) {
			return optionalCart.get();
		}
		throw new InvalidCartException("This cart is not valid!");
	}

	@Override
	public List<Map<String, Object>> updateCart(Cart cart) throws UnableToUpdateCartException {
		return null;		
	}

}
