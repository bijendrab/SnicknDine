package com.service;

import java.util.List;

import com.model.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	Product getProductById(int productId);

	void deleteProduct(int productId);
	
	void addProduct(Product product);
	
	void editProduct(Product product);

	void disableProduct(int productId);

	void enableProduct(int productId);

}
