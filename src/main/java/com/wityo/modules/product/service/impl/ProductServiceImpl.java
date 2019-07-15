package com.wityo.modules.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.model.ProductQuantityOption;
import com.wityo.modules.product.repository.ProductRepository;
import com.wityo.modules.product.service.ProductService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.CustomerRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	
	
}
