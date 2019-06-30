package com.dataTransferObjects;

import java.util.List;
import java.util.Map;

public class ImmediateRequestDTO {

    private List<Map<String,Object>> cartItemsForImmediate;

    public ImmediateRequestDTO(){}

    public List<Map<String, Object>> getCartItems() {
        return cartItemsForImmediate;
    }

    public void setCartItems(List<Map<String, Object>> cartItemsForImmediate) {
        this.cartItemsForImmediate = cartItemsForImmediate;
    }

    @Override
    public String toString() {
        return "ImmediateRequestDTO{" +
                "cartItemsForImmediate=" + cartItemsForImmediate +
                '}';
    }
}
