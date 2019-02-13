package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cartitem")
public class CartItem implements Serializable {

	private static final long serialVersionUID = -2455760938054036364L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartItemId;

	private int quality;

	private double price;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "cartId")
	@JsonIgnore
	private Cart cart;

	public CartItem() {
	}

	protected boolean canEqual(Object other) {
		return other instanceof CartItem;
	}

}
