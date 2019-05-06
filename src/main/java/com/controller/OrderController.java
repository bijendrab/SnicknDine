package com.controller;

import com.model.*;
import com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.service.CartService;
import com.service.CustomerOrderService;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@RequestMapping("/order/{cartId}")
	public String createOrder(@PathVariable("cartId") int cartId) {

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
		System.out.println("bijendra");
		return "redirect:/checkout?cartId=" + cartId;
	}
	@RequestMapping(value = {"/orderList" })
	public String getCustomerOrder(Model model) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailId = user.getUsername();
		Customer customer = customerService.getCustomerByEmailId(emailId);
		List<OrderItem> customerOrd = customerOrderService.getCustomerOrder();
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
