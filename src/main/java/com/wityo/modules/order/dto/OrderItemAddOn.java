package com.wityo.modules.order.dto;

public class OrderItemAddOn {
    private String orderItemAddOnId;
    private int itemId;
    private String itemName;
    private double price;

    public String getOrderItemAddOnId() {
        return orderItemAddOnId;
    }

    public void setOrderItemAddOnId(String orderItemAddOnId) {
        this.orderItemAddOnId = orderItemAddOnId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
