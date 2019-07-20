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
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.order.model.OrderStatus;
import com.wityo.modules.order.model.Reservation;
import com.wityo.modules.order.repository.CustomerOrderRepository;
import com.wityo.modules.order.repository.OrderItemRepository;
import com.wityo.modules.order.repository.ReservationRepository;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.user.model.User;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CustomerOrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	

	@Override
	public Reservation findReservationByOrder(CustomerOrder order) {
		return null;
	}

	@Override
	public CustomerOrder saveOrderForReservation(Reservation reservation, CustomerOrder newOrder) {
		return null;
	}

	@Override
	public CustomerOrder getOrderById(Long id) {
		Optional<CustomerOrder> optionalOrder = orderRepository.findById(id);
		if(optionalOrder.isPresent()) {
			return optionalOrder.get();
		}
		return null;
	}

	@Override
	public CustomerOrder changeOrderStatus(CustomerOrder order, OrderStatus orderStatus) {
		return null;
	}

	@Override
	public CustomerOrder confirmOrder(Long orderId) {
		return null;
	}

	@Override
	public CustomerOrder addItemToOrder(CustomerOrder order, Product newItem, Long itemOrderedNumber) {
		return null;
	}

	@Override
	public CustomerOrder findOrderByReservation(Reservation resevation) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Reservation reservation = reservationRepository.findByCustomer(user.getCustomer().getCustomerId());
		CustomerOrder order = orderRepository.findByReservation(reservation.getReservationId());
		return order;
	}

	@Override
	public CustomerOrder processOrderRequest(Long cartId, Long customerId, ImmediateRequestDto requestDto) {
		Reservation reservation = reservationRepository.findByCustomer(customerId);
		CustomerOrder order = new CustomerOrder();
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
			orderItem.setCustomerOrder(order);
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
//		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
