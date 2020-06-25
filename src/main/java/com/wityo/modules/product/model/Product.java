package com.wityo.modules.product.model;

import java.util.Set;

public class Product {
    private String productId;
    private Category category;
    private SubCategory subCategory;
    private Cuisine cuisine;
    private String description;
    private String productName;
    private boolean isAdd;
    private boolean isVeg;
    private boolean isEnabled;
    private int preparationTime;
    private String selectedQuantity;
    private int sequenceId;
    private Set<ProductQuantityOption> productQuantityOptions;
    private Set<AddOnProfile> addOnProfiles;

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Cuisine getCuisine() {
		return cuisine;
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getIsAdd() {
        return this.isAdd;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
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


    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(String selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public Set<ProductQuantityOption> getProductQuantityOptions() {
        return productQuantityOptions;
    }

    public void setProductQuantityOptions(Set<ProductQuantityOption> productQuantityOptions) {
        this.productQuantityOptions = productQuantityOptions;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Set<AddOnProfile> getAddOnProfiles() {
        return addOnProfiles;
    }

    public void setAddOnProfiles(Set<AddOnProfile> addOnProfiles) {
        this.addOnProfiles = addOnProfiles;
    }
}
