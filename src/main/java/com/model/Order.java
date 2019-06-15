package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "foodorder")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId")
    private int orderId;

    @Column(name = "totalCost")
    private Float totalCost;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservationId")
    private Reservation accordingReservation;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OrderItem> menuItemOrders=new HashSet<OrderItem>(0);

    //<editor-fold desc="constructor">
    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Reservation getAccordingReservation() {
        return accordingReservation;
    }

    public void setAccordingReservation(Reservation accordingReservation) {
        this.accordingReservation = accordingReservation;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<OrderItem> getMenuItemOrders() {
        return menuItemOrders;
    }

    public void setMenuItemOrders(Set<OrderItem> menuItemOrders) {
        this.menuItemOrders = menuItemOrders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", accordingReservation=" + accordingReservation +
                ", menuItemOrders=" + menuItemOrders +
                '}';
    }
}
