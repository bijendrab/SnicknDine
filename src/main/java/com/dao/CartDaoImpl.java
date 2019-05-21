package com.dao;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	public List<Map<String, Object>> update(Cart cart) {
		//Cart cart = getCartByCartId(cartId);
		int cartId = cart.getCartId();
		double grandTotal = customerOrderService.getCustomerOrderGrandTotal(cartId);
		cart.setTotalPrice(grandTotal);
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(cart);
		session.flush();
		session.close();
		List<Map<String, Object>> orderitems=UpdateOrderItem(cart);
		removeFromCart(cart);
		return orderitems;
	}

	public List<Map<String, Object>> UpdateOrderItem(Cart cart) {
		List<CartItem> cartItems = cart.getCartItem();
		List<Map<String, Object>> allOrderItem=new ArrayList<>();
		ObjectMapper Obj = new ObjectMapper();
		OrderItem orderItem = new OrderItem();
        Date creationDate = new Date();
		for (int i = 0; i < cartItems.size(); i++) {
			orderItem.setQuantity(cartItems.get(i).getQuantity());
			orderItem.setItemName(cartItems.get(i).getItemName());
			orderItem.setPrice(cartItems.get(i).getPrice());
			orderItem.setCartId(cart.getCartId());
			orderItem.setOrderCreationTime(creationDate);
			orderItem.setStatus("Unprocessed");
			orderItem.setQuantityOption(cartItems.get(i).getQuantityOption());
			try {
				String jsonStr = Obj.writeValueAsString(orderItem);
				Map<String, Object> map = Obj.readValue(jsonStr, Map.class);
				allOrderItem.add(map);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			Session sessionUpdate = sessionFactory.openSession();
			sessionUpdate.save(orderItem);
			sessionUpdate.flush();
			sessionUpdate.close();
		}
		return allOrderItem;

	}
    @Transactional
	public void removeFromCart(Cart cart) {
		List<CartItem> cartItems = cart.getCartItem();
		for (CartItem cartItem : cartItems) {
			Session sessionDelete = sessionFactory.openSession();
			CartItem cartItemDelete = (CartItem) sessionDelete.get(CartItem.class, cartItem.getCartItemId());
            System.out.println("CartItemId :" +cartItem.getCartItemId());
            Query query = sessionDelete.createQuery("delete CartItem where cartItemId = :cartItemId");
            query.setParameter("cartItemId", cartItem.getCartItemId());
            int result = query.executeUpdate();
            if (result >= 0) {
                System.out.println("products are removed");
            }
			Cart cart1 = cartItemDelete.getCart();
			List<CartItem> cartItems1 = cart1.getCartItem();
			cartItems1.remove(cartItemDelete);
			sessionDelete.flush();
			sessionDelete.close();
		}
	}

}
