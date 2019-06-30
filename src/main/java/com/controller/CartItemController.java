package com.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.service.CartItemService;
import com.service.CartService;
import com.service.CustomerService;
import com.service.ProductService;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class CartItemController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	public CartItemService getCartItemService() {
		return cartItemService;
	}

	public void setCartItemService(CartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}

	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value="/cart/add/{productId}/{quantityOption}",method = RequestMethod.POST)
	public String addCartItem(@PathVariable(value = "productId") int productId,
							  @PathVariable(value = "quantityOption") String quantityOption, Model model) {
		Cart cart = getCart();
		List<CartItem> cartItems = cart.getCartItem();
		Product product = productService.getProductById(productId);
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			if (product.getProductId()==cartItem.getProduct().getProductId()) {
				for (ProductQuantityOptions qOption:product.getQuantityOption()) {
					if(qOption.getOption().equals(quantityOption)&&((cartItem.getQuantityOption().equals(quantityOption)))) {
						cartItem.setQuantity(cartItem.getQuantity() + 1);
						cartItem.setPrice(cartItem.getQuantity() * qOption.getPrice());
					}
					if((!cartItem.getQuantityOption().equals(quantityOption) && qOption.getOption().equals(quantityOption))){
						cartItem.setPrice(cartItem.getQuantity() * qOption.getPrice());
						cartItem.setQuantityOption(qOption.getOption());
					}
				}
				cartItemService.addCartItem(cartItem);
				HashMap<String, Object> cartItemMap = getStringObjectHashMap(cartItem);
				model.addAttribute("cartItemDetails", cartItemMap);
				return "jsonTemplate";
			}
		}
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(1);
		cartItem.setProduct(product);
		cartItem.setItemName(product.getName());
		for (ProductQuantityOptions qOption:product.getQuantityOption()) {
			if (qOption.getOption().equals(quantityOption)) {
				cartItem.setPrice(qOption.getPrice() * 1);
				cartItem.setQuantityOption(quantityOption);
				break;
			}
		}
		cartItem.setCart(cart);
		cartItemService.addCartItem(cartItem);
		HashMap<String, Object> cartItemMap = getStringObjectHashMap(cartItem);
		model.addAttribute("cartItemDetails", cartItemMap);
		return "jsonTemplate";
	}
	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value = "/cart/decreaseCartItem/{productId}/{quantityOption}",method = RequestMethod.POST)
	//@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public String removeCartItemQuantity(@PathVariable(value = "productId") int productId,
									   @PathVariable(value = "quantityOption") String quantityOption, Model model) {
		Cart cart = getCart();
		List<CartItem> cartItems = cart.getCartItem();
		Product product = productService.getProductById(productId);
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			if (product.getProductId()==cartItem.getProduct().getProductId()) {
				cartItem.setQuantity(cartItem.getQuantity() - 1);
				for (ProductQuantityOptions qOption:product.getQuantityOption()) {
					if(qOption.getOption().equals(quantityOption)&&
							cartItem.getQuantityOption().equals(quantityOption)) {
						cartItem.setPrice(cartItem.getQuantity() * qOption.getPrice());
					}
				}
				cartItemService.addCartItem(cartItem);
				if(cartItem.getQuantity()==0){
					cartItemService.removeCartItem(cartItem.getCartItemId());
				}
				HashMap<String, Object> cartItemMap = getStringObjectHashMap(cartItem);
				model.addAttribute("cartItemDetails", cartItemMap);
				return "jsonTemplate";

			}
		}
		return "jsonTemplate";
	}
	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value = "/cart/removeCartItem/{cartItemId}",method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeCartItem(@PathVariable(value = "cartItemId") int cartItemId) {
		Cart cart = getCart();
		List<CartItem> cartItems = cart.getCartItem();
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			if (cartItem.getCartItemId()==cartItemId) {
				cartItemService.removeCartItem(cartItemId);
				break;
			}
		}
	}

	@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4201"})
	@RequestMapping(value="/cart/removeAllItems",method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeAllCartItems() {
		Cart cart = getCart();
		Cart cartNew = cartService.getCartByCartId(cart.getCartId());
		cartItemService.removeAllCartItems(cartNew);
	}
	private HashMap<String, Object> getStringObjectHashMap(CartItem cartItem) {
		HashMap<String, Object> cartItemMap=new HashMap<>();
		cartItemMap.put("cartItemId",cartItem.getCartItemId());
		cartItemMap.put("itemName",cartItem.getItemName());
		cartItemMap.put("quantityOption",cartItem.getQuantityOption());
		cartItemMap.put("price",cartItem.getPrice());
		cartItemMap.put("quantity",cartItem.getQuantity());
		cartItemMap.put("cartId",cartItem.getCart().getCartId());
		cartItemMap.put("productId",cartItem.getProduct().getProductId());
		return cartItemMap;
	}
	private Cart getCart() {

		String emailId = SecurityContextHolder.getContext().getAuthentication().getName();
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//String emailId = user.getUsername();
		Customer customer = customerService.getCustomerByEmailId(emailId);
		return customer.getCart();
	}

}
