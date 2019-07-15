package com.wityo.modules.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.repository.ProductRepository;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class CartItemServiceImpl {
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	public String addOrUpdateCart(Long productId, String quantityOptions) {
		try {
			User userDetail = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = userDetail.getCustomer();
			Cart cart = customer.getCart();
			Product product = productRepository.findByProductId(productId);
			
			List<CartItem> userCartItems = cart.getCartItems();

			if(userCartItems.size() == 0) {
				userCartItems = new ArrayList<CartItem>();
				CartItem newCartItem = new CartItem();
				newCartItem.setCart(cart);
				newCartItem.setItemName(product.getProductName());
				newCartItem.setQuantity(1);
				product.getProductQuantityOptions().forEach(qOption -> {
					if(qOption.getQuantityOption().equalsIgnoreCase(quantityOptions)) {
						newCartItem.setPrice(qOption.getPrice() * 1);
						newCartItem.setQuantityOption(quantityOptions);
					}
				});
				cartItemRepository.save(newCartItem);
				return "Item successfully added to cart";
			} else {
				userCartItems.parallelStream().forEach(cartItem -> {
					if(productId == cartItem.getProduct().getProductId()) {
						product.getProductQuantityOptions().parallelStream().forEach(qOption->{
							if(qOption.getQuantityOption().equalsIgnoreCase(quantityOptions)
								&& cartItem.getQuantityOption().equalsIgnoreCase(quantityOptions)) {
								cartItem.setQuantity(cartItem.getQuantity()+1);
								cartItem.setPrice(cartItem.getQuantity() * qOption.getPrice());
								cartItemRepository.save(cartItem);
							}
							if(!qOption.getQuantityOption().equalsIgnoreCase(quantityOptions)
									&& cartItem.getQuantityOption().equalsIgnoreCase(quantityOptions)) {
								cartItem.setPrice(cartItem.getQuantity() * qOption.getPrice());
								cartItem.setQuantityOption(qOption.getQuantityOption());
								cartItemRepository.save(cartItem);
							}
						});
					}
				});
				return "Cart Item updated successfully";
			}
		}catch (Exception e) {
		}
		return null;
		
	}
	
	public String deleteCartItemById(Long cartItemId) {
		try {
			cartItemRepository.deleteById(cartItemId);
			return "deleted";	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public String removeAllCartItems(Cart cart) {
		try {
			cart.getCartItems()
			.parallelStream()
			.forEach(cartItem -> cartItemRepository.deleteById(cartItem.getCartItemId()));
			return "all items deleted";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
