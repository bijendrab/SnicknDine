package com.wityo.modules.restaurant.dto;

import com.wityo.modules.product.model.Product;

import java.util.List;

public class RestaurantMenuListDto {

    List<Product> restaurantMenu;

    public List<Product> getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(List<Product> restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }
}
