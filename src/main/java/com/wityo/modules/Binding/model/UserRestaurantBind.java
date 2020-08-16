package com.wityo.modules.Binding.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_rest_bind")
public class UserRestaurantBind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bindId;
    private String userId;
    private Long restaurantId;
    private Boolean active;
    private Boolean cartStatus = Boolean.FALSE;
    private Boolean orderStatus =Boolean.FALSE;

    public Long getBindId() {
        return bindId;
    }

    public void setBindId(Long bindId) {
        this.bindId = bindId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(Boolean cartStatus) {
        this.cartStatus = cartStatus;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }
}
