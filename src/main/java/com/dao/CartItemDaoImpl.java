package com.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Cart;
import com.model.CartItem;

@Repository
@Transactional
public class CartItemDaoImpl implements CartItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Transactional
	public void addCartItem(CartItem cartItem) {
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(cartItem);
		session.flush();
		session.close();
	}
	@Transactional
	public void removeCartItem(int CartItemId) {
		Session session = sessionFactory.openSession();
		CartItem cartItem = (CartItem) session.get(CartItem.class, CartItemId);
		System.out.println(cartItem);
		session.delete(cartItem);
		Cart cart = cartItem.getCart();
		List<CartItem> cartItems = cart.getCartItem();
		cartItems.remove(cartItem);
		session.flush();
		session.close();
	}
	@Transactional
	public void removeAllCartItems(Cart cart) {
		List<CartItem> cartItems = cart.getCartItem();
		for (CartItem cartItem : cartItems) {
			removeCartItem(cartItem.getCartItemId());
		}
	}

}
