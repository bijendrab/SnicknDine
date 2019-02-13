package com.controller;

import com.model.Product;
import com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.model.Cart;
import com.model.Customer;
import com.model.CustomerOrder;
import com.service.CartService;
import com.service.CustomerOrderService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
	public ModelAndView getCustomerOrder() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailId = user.getUsername();
		Customer customer = customerService.getCustomerByemailId(emailId);
		List<Cart> customerOrd = customerOrderService.getCustomerOrder();
		return new ModelAndView("CustomerOrderPage", "customerOrd", customerOrd);
	}
}
