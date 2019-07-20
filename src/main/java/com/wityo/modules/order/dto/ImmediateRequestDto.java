package com.wityo.modules.order.dto;

import java.util.List;
import java.util.Map;

public class ImmediateRequestDto {
	
	private List<Map<String, Object>> cartItemsForImmediateOrder;

	public List<Map<String, Object>> getCartItemsForImmediateOrder() {
		return cartItemsForImmediateOrder;
	}

	public void setCartItemsForImmediateOrder(List<Map<String, Object>> cartItemsForImmediateOrder) {
		this.cartItemsForImmediateOrder = cartItemsForImmediateOrder;
	}

	@Override
	public String toString() {
		return "ImmediateRequestDto [cartItemsForImmediateOrder=" + cartItemsForImmediateOrder + "]";
	}

}
