package com.wityo.modules.order.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.repository.CartRepository;
import com.wityo.modules.order.dto.ImmediateRequestDto;
import com.wityo.modules.order.model.Order;
import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.order.model.OrderStatus;
import com.wityo.modules.order.model.Reservation;
import com.wityo.modules.order.repository.OrderItemRepository;
import com.wityo.modules.order.repository.OrderRepository;
import com.wityo.modules.order.repository.ReservationRepository;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.user.model.User;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public String updateOrderItem(OrderItem orderItem) {
		Optional<OrderItem> optionalItem = orderItemRepository.findById(orderItem.getOrderItemId());
		OrderItem existingOrderItem = null;
		if(optionalItem.isPresent()) {
			existingOrderItem = optionalItem.get();
			return "updated";
		}
		return "not updated";
	}

	@Override
	public Reservation findReservationByOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order saveOrderForReservation(Reservation reservation, Order newOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getOrderById(Long id) {
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if(optionalOrder.isPresent()) {
			return optionalOrder.get();
		}
		return null;
	}

	@Override
	public Order changeOrderStatus(Order order, OrderStatus orderStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order confirmOrder(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order addItemToOrder(Order order, Product newItem, Long itemOrderedNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOrderByReservation(Reservation resevation) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Reservation reservation = reservationRepository.findByCustomerId(user.getCustomer().getCustomerId());
		Order order = orderRepository.findByReservationId(reservation.getReservationId());
		return order;
	}

	@Override
	public Order processOrderRequest(Long cartId, Long customerId, ImmediateRequestDto requestDto) {

		Reservation reservation = reservationRepository.findByCustomerId(customerId);
		Order order = new Order();
		order.setReservation(reservation);
		order.setStatus(OrderStatus.ON_HOLD);
		Cart cart = null;
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
		double totalPrice = 0;
		if(optionalCart.isPresent()) {
			cart = optionalCart.get();
		}
		LocalDateTime orderCreationTime = LocalDateTime.now();
		for(CartItem cartItem : cart.getCartItems()) {
			totalPrice += cartItem.getPrice();
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setItemName(cartItem.getItemName());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setOrderCreationTime(orderCreationTime);
			orderItem.setStatus("Unprocessed");
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setCart(cart);
			orderItem.setOrder(order);
			for(Map<String, Object> iRItem : requestDto.getCartItemsForImmediateOrder()) {
				if(iRItem.get("cartItemId") == cartItem.getCartItemId()) {
					orderItem.setImmediateStatus((Boolean)iRItem.get("immediateStatus"));
					break;
				}
			}
			order.getMenuItemOrders().add(orderItem);
		};
		order.setTotalCost(totalPrice);
		return orderRepository.save(order);
	}
	
	
	public List<Map<Integer, List<OrderItem>>> getUserOrdersForOrderList(){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return null;
	}
	
	public boolean deleteCustomerOrderItem(Long orderItemId) {
		Optional<OrderItem> optionalOi = orderItemRepository.findById(orderItemId);
		if(optionalOi.isPresent()) {
			orderItemRepository.deleteById(orderItemId);
			return true;
		}
		return false;
	}
	
	
}
