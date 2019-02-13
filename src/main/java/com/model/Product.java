package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

@Data
@Entity
@Table(name = "item")
public class Product implements Serializable {

	private static final long serialVersionUID = 5186013952828648626L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	
	@Column(name="category")
	private String productCategory;

	@Column(name="Type")
	private String productType;

	@Column(name="Cuisine")
	private String productCuisine;

	@Column(name = "description")
	private String productDescription;
	
	/*@Column(name = "manufacturer")
	private String productManufacturer;*/
	
	@NotEmpty(message = "Product Name is mandatory")
	@Column(name = "name")
	private String productName;
	
	@NotNull(message="Please provide some price")
	@Min(value = 100, message = "Minimum value should be greater than 100")
	@Column(name = "price")
	private double productPrice;
	
	@Column(name = "unit")
	private int unitStock;

	@Column(name = "itemStatus")
	private boolean itemStatus;

	@Transient
	private MultipartFile productImage;



	public Product(String productCategory, String productType, String productCuisine, String productDescription, String productName, double productPrice, int unitStock,boolean itemStatus, MultipartFile productImage) {
		this.productCategory = productCategory;
		this.productType = productType;
		this.productCuisine = productCuisine;
		this.productDescription = productDescription;
		//this.productManufacturer = productManufacturer;
		this.productName = productName;
		this.productPrice = productPrice;
		this.unitStock = unitStock;
		this.productImage = productImage;
		this.itemStatus=itemStatus;
	}

	public Product() {


	}

}
