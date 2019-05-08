package com.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.model.*;
import com.service.CustomerService;
import com.service.ProductService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.service.CustomerOrderService;

@Repository
@Transactional
public class CartDaoImpl implements CartDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Transactional
	public Cart getCartByCartId(int CartId) {
		Session sessionRet = sessionFactory.openSession();
		Cart cart = (Cart) sessionRet.get(Cart.class, CartId);
		System.out.println(cart.getCartId() + " " + cart.getCartItem());
		System.out.println(cart);
		sessionRet.close();
		return cart;

	}

	public Cart validate(int cartId) throws IOException {
		Cart cart = getCartByCartId(cartId);
		if (cart == null || cart.getCartItem().size() == 0) {
			throw new IOException(cartId + "");
		}
		double grandTotal = customerOrderService.getCustomerOrderGrandTotal(cartId);
		cart.setTotalPrice(grandTotal);
		return cart;
	}
	@Transactional
	public void update(Cart cart) {
		//Cart cart = getCartByCartId(cartId);
		int cartId = cart.getCartId();
		double grandTotal = customerOrderService.getCustomerOrderGrandTotal(cartId);
		cart.setTotalPrice(grandTotal);
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(cart);
		session.flush();
		session.close();
		UpdateOrderItem(cart);
		removeFromCart(cart);
	}

	public void UpdateOrderItem(Cart cart) {
		List<CartItem> cartItems = cart.getCartItem();
		OrderItem orderItem = new OrderItem();
        Date creationDate = new Date();
		for (int i = 0; i < cartItems.size(); i++) {
			orderItem.setQuality(cartItems.get(i).getQuality());
			orderItem.setProductId(cartItems.get(i).getProduct().getProductId());
			orderItem.setPrice(cartItems.get(i).getPrice());
			orderItem.setCartId(cart.getCartId());
			orderItem.setOrderCreationTime(creationDate);
			orderItem.setStatus("Unprocessed");
			//orderItem.setWaitTime(cartItems.get(i).getProduct().getPTime());
			Session sessionUpdate = sessionFactory.openSession();
			System.out.println(orderItem.getProductId()+" "+orderItem.getPrice()+" "+ orderItem.getCartId());
			sessionUpdate.save(orderItem);
			sessionUpdate.flush();
			sessionUpdate.close();
		}
	}
    @Transactional
	public void removeFromCart(Cart cart) {
		List<CartItem> cartItems = cart.getCartItem();
		for (CartItem cartItem : cartItems) {
			Session sessionDelete = sessionFactory.openSession();
			CartItem cartItemDelete = (CartItem) sessionDelete.get(CartItem.class, cartItem.getCartItemId());
			/*System.out.println(cartItemDelete);
			sessionDelete.delete(cartItemDelete);
            sessionDelete.getTransaction().commit();*/
            System.out.println("CartItemId :" +cartItem.getCartItemId());
            Query query = sessionDelete.createQuery("delete CartItem where cartItemId = :cartItemId");
            query.setParameter("cartItemId", cartItem.getCartItemId());
            int result = query.executeUpdate();
            if (result >= 0) {
                System.out.println("Expensive products was removed");
            }
			Cart cart1 = cartItemDelete.getCart();
			List<CartItem> cartItems1 = cart1.getCartItem();
			cartItems1.remove(cartItemDelete);
			sessionDelete.flush();
			sessionDelete.close();
		}
	}

}
