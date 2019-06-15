package com.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "item")
public class Product implements Serializable {

	private static final long serialVersionUID = 5186013952828648626L;

	@Id
	@Column(name = "productId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;
	
	@Column(name="category")
	private String category;

    @Column(name="subCategory")
    private String subCategory;

	@Column(name="cuisine")
	private String cuisine;

	@Column(name = "description")
	private String description;
	
	@NotEmpty(message = "Product Name is mandatory")
	@Column(name = "name")
	private String name;


    @Column(name = "isAdd")
    private Boolean isAdd;


	@Column(name = "isVeg")
	private Boolean isVeg;

	@Column(name = "isEnabled")
	private Boolean isEnabled;

	@Column(name = "prepTime")
	private int prepTime;

    @Column(name="selectedQuantity")
	private  String selectedQuantity;


	@OneToMany(mappedBy = "product",orphanRemoval = true, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<ProductQuantityOptions> quantityOption;

	/*@OneToMany(mappedBy = "product",orphanRemoval = true, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<CartItem> cartItems;*/


	/*public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}*/

	public Product() {


	}

	public Boolean getIsVeg() {
		return this.isVeg;
	}

	public void setIsVeg(Boolean isVeg) {
		this.isVeg = isVeg;
	}

	public Boolean getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

	public int getProductId() {
		return this.productId;
	}

	public String getCategory() {
		return this.category;
	}

	public String getSubCategory() {
		return this.subCategory;
	}

	public String getCuisine() {
		return this.cuisine;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getIsAdd() {
		return this.isAdd;
	}

	public String getSelectedQuantity() {
		return this.selectedQuantity;
	}

	public Set<ProductQuantityOptions> getQuantityOption() {
		return this.quantityOption;
	}

	/*public MultipartFile getProductImage() {
		return this.productImage;
	}*/

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public void setSelectedQuantity(String selectedQuantity) {
		this.selectedQuantity = selectedQuantity;
	}

	public void setQuantityOption(Set<ProductQuantityOptions> quantityOption) {
		this.quantityOption = quantityOption;
	}

	/*public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}*/

	@Override
	public String toString() {
		return "Product{" +
				"productId=" + productId +
				", category='" + category + '\'' +
				", subCategory='" + subCategory + '\'' +
				", cuisine='" + cuisine + '\'' +
				", description='" + description + '\'' +
				", name='" + name + '\'' +
				", isAdd=" + isAdd +
				", isVeg=" + isVeg +
				", isEnabled=" + isEnabled +
				", prepTime=" + prepTime +
				", selectedQuantity='" + selectedQuantity + '\'' +
				", quantityOption=" + quantityOption +
				//", cartItems=" + cartItems +
				'}';
	}
}
