package com.wityo.modules.order.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.wityo.modules.order.dto.OrderItemAddOn;

public class OrderItem {

    private String orderItemId;
    private Date orderCreationTime;
    private int quantity;
    private double price;
    private String status;
    private String itemName;
    private double waitTime;
    private String quantityOption;
    private Boolean immediateStatus;
    private Set<OrderItemAddOn> orderItemAddOns = new HashSet<>();
    private String customerCartItems;
    private Boolean specialDiscount;
    private float specialDiscountValue;

    public OrderItem() {
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOptions) {
        this.quantityOption = quantityOptions;
    }

    public Boolean getImmediateStatus() {
        return immediateStatus;
    }

    public void setImmediateStatus(Boolean immediateStatus) {
        this.immediateStatus = immediateStatus;
    }

    public Date getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(Date orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public String getCustomerCartItems() {
        return customerCartItems;
    }

    public void setCustomerCartItems(String customerCartItems) {
        this.customerCartItems = customerCartItems;
    }


    public Boolean getSpecialDiscount() {
        return specialDiscount;
    }

    public void setSpecialDiscount(Boolean specialDiscount) {
        this.specialDiscount = specialDiscount;
    }

    public float getSpecialDiscountValue() {
        return specialDiscountValue;
    }

    public void setSpecialDiscountValue(float specialDiscountValue) {
        this.specialDiscountValue = specialDiscountValue;
    }

    public Set<OrderItemAddOn> getOrderItemAddOns() {
        return orderItemAddOns;
    }

    public void setOrderItemAddOns(Set<OrderItemAddOn> orderItemAddOns) {
        this.orderItemAddOns = orderItemAddOns;
    }
}
