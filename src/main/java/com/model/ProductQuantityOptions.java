package com.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "itemQuantity")
public class ProductQuantityOptions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int QOId;

    @Column(name="qoption")
    private String option;

    @Column(name = "quantity")
    private int quantity;

    @NotNull(message="Please provide some price")
    @Min(value = 50, message = "Minimum value should be greater than 50")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductQuantityOptions() {
    }

    protected boolean canEqual(Object other) {
        return other instanceof ProductQuantityOptions;
    }

    public int getQOId() {
        return this.QOId;
    }

    public String getOption() {
        return this.option;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setQOId(int QOId) {
        this.QOId = QOId;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
