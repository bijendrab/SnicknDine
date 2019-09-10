package com.wityo.modules.product.model;

public class ProductQuantityOption {
    private Long productQuantityOptionId;
    private String quantityOption;
    private String quantity;
    private double price;

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
}
