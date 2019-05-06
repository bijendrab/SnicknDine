package com.model;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "item")
//@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Product implements Serializable {

	private static final long serialVersionUID = 5186013952828648626L;

	@Id
	@Column(name = "productId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	@Column(name="category")
	private String category;

	/*@Column(name="Type")
	private String productType;*/
    @Column(name="subCategory")
    private String subCategory;

	@Column(name="cuisine")
	private String cuisine;

	@Column(name = "description")
	private String description;
	
	/*@Column(name = "manufacturer")
	private String productManufacturer;*/
	
	@NotEmpty(message = "Product Name is mandatory")
	@Column(name = "name")
	private String name;
	
	/*@NotNull(message="Please provide some price")
	@Min(value = 100, message = "Minimum value should be greater than 100")
	@Column(name = "price")
	private double productPrice;*/
	
	/*@Column(name = "unit")
	private int unitStock;*/

	/*@Column(name = "itemStatus")
	private boolean itemStatus;*/

    @Column(name = "isAdd")
    private Boolean isAdd;

	/*@Column(name= "preparationTime")
	private double pTime;*/

    @Column(name="selectedQuantity")
	private  String selectedQuantity;


	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<ProductQuantityOptions> quantityOption;

	/*@Transient
	private MultipartFile productImage;*/



	public Product() {


	}

    public int getId() {
        return this.productId;
    }

    public void setId(int Id) {
        this.productId = Id;
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

	public List<ProductQuantityOptions> getQuantityOption() {
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

	public void setQuantityOption(List<ProductQuantityOptions> quantityOption) {
		this.quantityOption = quantityOption;
	}

	/*public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}*/
}
