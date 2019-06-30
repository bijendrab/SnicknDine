package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDao;
import com.model.Cart;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDao cartDao;

	public CartDao getCartDao() {
		return cartDao;
	}

	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	public Cart getCartByCartId(int CartId) {

		return cartDao.getCartByCartId(CartId);
	}
	public Cart validate(int CartId) {
		Cart cart= null;
		try {
			cart =cartDao.validate(CartId);
		} catch (Exception e) {
			System.out.println("Exception occurred");
		}
		return cart;
	}
	public List<Map<String, Object>> update(Cart cart){
		List<Map<String, Object>> orderItems=cartDao.update(cart);
		return orderItems;
	}
}
