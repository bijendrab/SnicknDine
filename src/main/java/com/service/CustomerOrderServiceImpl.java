package com.service;

import java.util.List;
import java.util.Set;

import com.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CustomerOrderDao;
import com.model.Cart;
import com.model.CartItem;
import com.model.CustomerOrder;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private CustomerOrderDao customerOrderDao;
	
	@Autowired
	private CartService cartService;

	
	public void addCustomerOrder(CustomerOrder customerOrder) {
		customerOrderDao.addCustomerOrder(customerOrder);
	}

	public double getCustomerOrderGrandTotal(int cartId) {
		double grandTotal=0;
		Cart cart = cartService.getCartByCartId(cartId);
		List<CartItem> cartItems = cart.getCartItem();


		for(CartItem item: cartItems){
			grandTotal += item.getPrice();
		}
		return grandTotal;
	}
	public List<Cart> getCustomerOrder() {
		return customerOrderDao.getCustomerOrderByCustomerId();
	}

}
