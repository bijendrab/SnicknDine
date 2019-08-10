package com.wityo.modules.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wityo.common.Constant;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	RestTemplate restTemplate;
	
	public CustomerOrder placeCustomerOrder(PlaceOrderDTO order, Long restaurantId) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			order.setCustomer(customer);
			CustomerOrder placedOrder = restTemplate
					.postForObject(Constant.RESTAURANT_SERVER_URL+"api/order/checkout/"+restaurantId,
							order,
							CustomerOrder.class);
			return placedOrder;
		}catch (Exception e) {
			// exception to be thrown
		}
		return null;
		
	}
	

}
