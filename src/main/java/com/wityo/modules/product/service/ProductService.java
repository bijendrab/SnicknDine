package com.wityo.modules.product.service;

import java.util.List;

import com.wityo.common.exception.WityoGenericException;
import com.wityo.modules.product.exception.DuplicateProductException;
import com.wityo.modules.product.exception.EditProductException;
import com.wityo.modules.product.exception.NoProductsFoundException;
import com.wityo.modules.product.exception.ProductAdditionException;
import com.wityo.modules.product.exception.ProductNotFoundException;
import com.wityo.modules.product.exception.ProductStatusChangeException;
import com.wityo.modules.product.exception.UnableToDeleteProductException;
import com.wityo.modules.product.model.Product;

public interface ProductService {
	
	public List<Product> fetchAllProducts() throws NoProductsFoundException, WityoGenericException;
	public Product getProductById(String productId) throws ProductNotFoundException, WityoGenericException;
	public boolean deleteProductById(String productId) throws UnableToDeleteProductException;
	public boolean changeProductStatusById(String productId)throws ProductStatusChangeException;
	public Product addProduct(Product product) throws ProductAdditionException, DuplicateProductException;
	public Product updateProduct(Product latestProduct) throws EditProductException;
	
}
