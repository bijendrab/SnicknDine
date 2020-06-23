package com.wityo.modules.cart.model;

import java.util.List;
import com.wityo.modules.product.model.Product;

public class UserCartItem {
    private Product product;
    private List<SelectCartAddOnItems> selectCartAddOnItems;
    private String userSelectQuantityOption;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
