package com.wityo.modules.cart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.model.ProductQuantityOption;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	CartItemRepository cartItemRepository;


	public String addOrUpdateCart(Product product, String quantityOptions) {
		try {
			User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = userDetail.getCustomer();
			Cart cart = customer.getCart();

			List<CartItem> userCartItems = cart.getCartItems();
			String productId = product.getProductId();
			CartItem tempCartItem = null;
			for(CartItem cartItem : userCartItems) {
				String productJson = cartItem.getProductJson();
				Product p = new Gson().fromJson(productJson, Product.class);
				if(productId.equalsIgnoreCase(p.getProductId())) {
					tempCartItem = cartItem;
					break;
				}
			}
			if (tempCartItem != null) {
				String productJson = tempCartItem.getProductJson();
				Product tempProduct = new Gson().fromJson(productJson, Product.class);
				Set<ProductQuantityOption> productQuantityOptions = tempProduct.getProductQuantityOptions();
				for (ProductQuantityOption qOption : productQuantityOptions) {
					if (qOption.getQuantityOption().equalsIgnoreCase(quantityOptions)
							&& tempCartItem.getQuantityOption().equalsIgnoreCase(quantityOptions)) {
						tempCartItem.setQuantity(tempCartItem.getQuantity() + 1);
						tempCartItem.setPrice(tempCartItem.getQuantity() * qOption.getPrice());
						cartItemRepository.save(tempCartItem);
						break;
					}
					if (qOption.getQuantityOption().equalsIgnoreCase(quantityOptions)
							&& !tempCartItem.getQuantityOption().equalsIgnoreCase(quantityOptions)) {
						tempCartItem.setPrice(tempCartItem.getQuantity() * qOption.getPrice());
						tempCartItem.setQuantityOption(qOption.getQuantityOption());
						cartItemRepository.save(tempCartItem);
						break;
					}
				}
				return "updated";
			}
			CartItem newCartItem = new CartItem();
			newCartItem.setCart(cart);
			newCartItem.setItemName(product.getProductName());
			newCartItem.setQuantity(1);
			newCartItem.setProductJson(new Gson().toJson(product));
			product.getProductQuantityOptions().forEach(qOption -> {
				if (qOption.getQuantityOption().equalsIgnoreCase(quantityOptions)) {
					newCartItem.setPrice(qOption.getPrice() * 1);
					newCartItem.setQuantityOption(quantityOptions);
				}
			});
			cartItemRepository.save(newCartItem);
			return "added";

		} catch (Exception e) {
		}
		return "error";

	}

	public String deleteCartItemById(Long cartItemId) {
		try {
			cartItemRepository.deleteById(cartItemId);
			return "deleted";
		} catch (Exception e) {
		}
		return "failure";
	}

	public String removeAllCartItems(Cart cart) {
		try {
			cart.getCartItems().parallelStream()
					.forEach(cartItem -> cartItemRepository.deleteById(cartItem.getCartItemId()));
			return "all items deleted";
		} catch (Exception e) {
		}
		return "failure";
	}
	
	public String reduceCartItem(String productId, String quantityOption){
		User userDetail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Customer customer = userDetail.getCustomer();
		Cart cart = customer.getCart();
		for(CartItem cartItem : cart.getCartItems()) {
			String productJson = cartItem.getProductJson();
			Product product = new Gson().fromJson(productJson, Product.class);
			if(productId.equals(product.getProductId())) {
				int updatedQuantity = cartItem.getQuantity() - 1;
				if(updatedQuantity == 0) {
					cartItemRepository.deleteById(cartItem.getCartItemId());
					return "cart updated";
				}
				cartItem.setQuantity(updatedQuantity);
				for(ProductQuantityOption qOption : product.getProductQuantityOptions()) {
					if(qOption.getQuantityOption().equalsIgnoreCase(quantityOption) 
							&& cartItem.getQuantityOption().equalsIgnoreCase(quantityOption)) {
						cartItem.setPrice(updatedQuantity * qOption.getPrice());
						cartItemRepository.save(cartItem);
						return "cart updated";
					}
				}
				
			}
		}
		return "operation failed";
	}
}
