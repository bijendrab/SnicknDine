package com.controller;

import com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.service.CartService;
import com.service.CustomerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class CartController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value = "/cart/getCartById",method = RequestMethod.GET)
	public String getCartId(Model model){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailId = user.getUsername();
		Customer customer = customerService.getCustomerByEmailId(emailId);
		List<CartItem> cartItems = customer.getCart().getCartItem();
		List<HashMap<String, Object>> cartAllItems=new ArrayList<>();
		for(CartItem cartit:cartItems){
			HashMap<String, Object> opt = new HashMap<>();
			opt.put("cartItemId",cartit.getCartItemId());
			opt.put("itemName",cartit.getItemName());
			opt.put("price",cartit.getPrice());
			opt.put("quantity",cartit.getQuantity());
			opt.put("quantityOption",cartit.getQuantityOption());
			opt.put("productId",cartit.getProduct().getProductId());
			opt.put("category",cartit.getProduct().getCategory());
			opt.put("subCategory",cartit.getProduct().getSubCategory());
			List<HashMap<String, Object>> quantityOptions=new ArrayList<>();
			for (ProductQuantityOptions quantO:cartit.getProduct().getQuantityOption()){
				HashMap<String, Object> optcart = new HashMap<>();
				optcart.put("option",quantO.getOption());
				optcart.put("price",quantO.getPrice());
				optcart.put("quantity",quantO.getQuantity());
				quantityOptions.add(optcart);
			}
			opt.put("quantityOptions",quantityOptions);
			cartAllItems.add(opt);
		}
		model.addAttribute("cartItems", cartAllItems);
		return "jsonTemplate";
	}

	@RequestMapping("/cart/getCart/{cartId}")
	public @ResponseBody Cart getCartItems(@PathVariable(value="cartId")int cartId){
		return cartService.getCartByCartId(cartId);
	}
	
}
