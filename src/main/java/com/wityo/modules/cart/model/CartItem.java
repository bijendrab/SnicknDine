package com.wityo.modules.cart.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartItemId")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private Long restaurantId;
    private String itemName;
    private String quantityOption;
    private int quantity;
    private double price;
    private Boolean immediateStatus = Boolean.FALSE;
    private LocalDateTime addItemToCartTime;
    private LocalDateTime updateItemInCartTime;


    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @Lob
    private String productJson;

    @OneToMany(mappedBy = "cartItem", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SelectCartAddOnItems> selectCartAddOnItems;

    public CartItem() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }

    public Boolean getImmediateStatus() {
        return immediateStatus;
    }

    public void setImmediateStatus(Boolean immediateStatus) {
        this.immediateStatus = immediateStatus;
    }

    public Set<SelectCartAddOnItems> getSelectCartAddOnItems() {
        return selectCartAddOnItems;
    }

    public void setSelectCartAddOnItems(Set<SelectCartAddOnItems> selectCartAddOnItems) {
        this.selectCartAddOnItems = selectCartAddOnItems;
    }

    public LocalDateTime getAddItemToCartTime() {
        return addItemToCartTime;
    }

    public void setAddItemToCartTime(LocalDateTime addItemToCartTime) {
        this.addItemToCartTime = addItemToCartTime;
    }

    public LocalDateTime getUpdateItemInCartTime() {
        return updateItemInCartTime;
    }

    public void setUpdateItemInCartTime(LocalDateTime updateItemInCartTime) {
        this.updateItemInCartTime = updateItemInCartTime;
    }
}
