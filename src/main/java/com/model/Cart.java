package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

	private static final long serialVersionUID = 8436097833452420298L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartId;

	@OneToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<CartItem> cartItem;

	private double totalPrice;

    public Cart() {
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Cart;
	}

	public int getCartId() {
		return this.cartId;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public List<CartItem> getCartItem() {
		return this.cartItem;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setCartItem(List<CartItem> cartItem) {
		this.cartItem = cartItem;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}



	public Cart(int cartId, int customerId, double totalPrice) {
		this.cartId = cartId;
		this.customer = customer;
		this.cartItem=cartItem;
		this.totalPrice = totalPrice;
	}

}
