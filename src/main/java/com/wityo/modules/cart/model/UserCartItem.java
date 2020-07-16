package com.wityo.modules.cart.model;

import java.util.List;
import com.wityo.modules.product.model.Product;

public class UserCartItem {
    private String productId;
    private Long restaurantId;
    private List<SelectCartAddOnItems> selectCartAddOnItems;
    private String userSelectQuantityOption;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<SelectCartAddOnItems> getSelectCartAddOnItems() {
        return selectCartAddOnItems;
    }

    public void setSelectCartAddOnItems(List<SelectCartAddOnItems> selectCartAddOnItems) {
        this.selectCartAddOnItems = selectCartAddOnItems;
    }

    public String getUserSelectQuantityOption() {
        return userSelectQuantityOption;
    }

    public void setUserSelectQuantityOption(String userSelectQuantityOption) {
        this.userSelectQuantityOption = userSelectQuantityOption;
    }
}
