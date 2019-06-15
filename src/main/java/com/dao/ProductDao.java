package com.dao;

import java.util.List;

import com.model.Product;

public interface ProductDao {

	List<Product> getAllProducts();

	Product getProductById(int productId);

	void deleteProduct(int productId);

	void addProduct(Product product);
	
	void editProduct(Product product);

	void setProductStatus(int productId);
	
}
