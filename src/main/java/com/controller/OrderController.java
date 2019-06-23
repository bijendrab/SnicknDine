package com.controller;

import com.dataTransferObjects.OrderRequestDTO;
import com.model.*;
import com.service.CustomerService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.service.CartService;
import com.service.CustomerOrderService;

import java.util.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private OrderService orderRepo;

	@RequestMapping(value="/cart/checkout/{cartId}",method = RequestMethod.POST)
	public String createOrder(@PathVariable("cartId") int cartId, Model model) {

		/*CustomerOrder customerOrder = new CustomerOrder();

		Cart cart = cartService.getCartByCartId(cartId);
		// Update CartId for customerOrder - set CartId
		customerOrder.setCart(cart);

		Customer customer = cart.getCustomer();

		customerOrder.setCustomer(customer);*/
		// Set customerid
		// Set ShippingAddressId
		//customerOrder.setShippingAddress(customer.getShippingAddress());

		//customerOrder.setBillingAddress(customer.getBillingAddress());

		//customerOrderService.addCustomerOrder(customerOrder);

		Cart cart =cartService.validate(cartId);
		List<Map<String, Object>> orderItems= cartService.update(cart);
		model.addAttribute("OrderList", orderItems);
		return "jsonTemplate";
		//return "redirect:/checkout?cartId=" + cartId;
	}
	@RequestMapping(value="/cart/checkout/",method = RequestMethod.POST)
	public String RequestOrder(Model model) throws Exception {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailId = user.getUsername();
		Customer customer = customerService.getCustomerByEmailId(emailId);
		Order createdOrder = orderRepo.processOrderRequest(customer.getCart().getCartId(),customer.getCustomerId());
		model.addAttribute("OrderList", createdOrder);
		return "jsonTemplate";
		//return "redirect:/checkout?cartId=" + cartId;
	}
	@RequestMapping(value = {"/orderList" })
	public String getCustomerOrder(Model model) {
		List<Map<Integer,List<OrderItem>>> customerOrd = customerOrderService.getCustomerOrder();
		model.addAttribute("orderList", customerOrd);
		return "jsonTemplate";
	}

	@RequestMapping(value = "/updateOrderItem",method = RequestMethod.PUT,consumes="application/json")
	public String updateCustomerOrder(@RequestBody OrderItem orderitem, Model model) {
		/*if (result.hasErrors())
			return "CustomerOrderPage";*/
		orderitem.setStatus("Processed");
		customerOrderService.updateCustomerOrder(orderitem);
		model.addAttribute("updateOrderList", orderitem);
		return "jsonTemplate";
	}

}
