package com.wityo.modules.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wityo.common.Constant;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.TableOrdersResponse;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CartItemService cartItemService;

	@Autowired
	CartItemRepository cartItemRepository;
	
	public CustomerOrder placeCustomerOrder(PlaceOrderDTO order, Long restaurantId) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			order.setCustomer(customer);
			CustomerOrder placedOrder = restTemplate
					.postForObject(Constant.RESTAURANT_SERVER_URL+"api/customerOrder/checkout/"+restaurantId,
							order,
							CustomerOrder.class);
			if(placedOrder!=null) {
				cartItemRepository.deleteCartItemsByCartId(customer.getCart().getCartId());
				return placedOrder;
			}
		}catch (Exception e) {}
		return null;
	}
	
	public TableOrdersResponse getCustomerTableOrders(Long restaurantId) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			TableOrdersResponse response = restTemplate.postForObject(
					Constant.RESTAURANT_SERVER_URL+"api/customerOrder/getTableOrder/"+restaurantId,
					customer,
					TableOrdersResponse.class);
			return response;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
