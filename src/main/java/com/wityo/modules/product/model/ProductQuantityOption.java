package com.wityo.modules.product.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="productQuantityOptionId")
@Table(name="item_quantity")
public class ProductQuantityOption {
	@Id
	@Column(name = "pqo_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productQuantityOptionId;
	private String quantityOption;
	private String quantity;
	private double price;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;
	
	public ProductQuantityOption() {
	}

	public Long getProductQuantityOptionId() {
		return productQuantityOptionId;
	}

	public void setProductQuantityOptionId(Long productQuantityOptionId) {
		this.productQuantityOptionId = productQuantityOptionId;
	}

	public String getQuantityOption() {
		return quantityOption;
	}

	public void setQuantityOption(String quantityOption) {
		this.quantityOption = quantityOption;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
