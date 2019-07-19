package com.wityo.modules.order.service;

import com.wityo.modules.order.model.Order;
import com.wityo.modules.order.model.OrderStatus;
import com.wityo.modules.order.model.Reservation;
import com.wityo.modules.product.model.Product;

public interface OrderService {
	
	Reservation findReservationByOrder(Order order);
	Order saveOrderForReservation(Reservation reservation, Order newOrder);
	Order getOrderById(Long id);
	Order changeOrderStatus(Order order, OrderStatus orderStatus);
	Order confirmOrder(Long orderId);
	Order addItemToOrder(Order order, Product newItem, Long itemOrderedNumber);
	Order findOrderByReservation(Reservation resevation);
	Order processOrderRequest(Long cartId, Long customerId);
}
