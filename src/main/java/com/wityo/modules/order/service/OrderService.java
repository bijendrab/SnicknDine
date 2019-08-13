package com.wityo.modules.order.service;

import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.TableOrdersResponse;
import com.wityo.modules.order.model.CustomerOrder;

public interface OrderService {
	
	CustomerOrder placeCustomerOrder(PlaceOrderDTO order, Long restaurantId);
	public TableOrdersResponse getCustomerTableOrders(Long restaurantId);

}
