package com.wityo.modules.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "customer_order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderHistoryId")
    private Long orderHistoryId;

    @Lob
    @Column(name = "orders")
    private String orders;

    @Column(name = "tableId")
    private Long tableId;

    @Column(name = "paymentStatus")
    private Boolean paymentStatus;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name="totalCost")
    private double totalCost;

    @Column(name= "customerId")
    private Long customerId;

    @Column(name= "restaurantId")
    private Long restaurantId;

    @Column(name= "restaurantName")
    private String restaurantName;

    private LocalDateTime orderCreationTime;

    private LocalDateTime orderHistoryTime;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderHistoryId() {
        return orderHistoryId;
    }

    public void setOrderHistoryId(Long orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(LocalDateTime orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public LocalDateTime getOrderHistoryTime() {
        return orderHistoryTime;
    }

    public void setOrderHistoryTime(LocalDateTime orderHistoryTime) {
        this.orderHistoryTime = orderHistoryTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
