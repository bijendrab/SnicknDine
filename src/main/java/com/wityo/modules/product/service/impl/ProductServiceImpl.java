package com.wityo.modules.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityo.common.exception.WityoGenericException;
import com.wityo.modules.product.exception.DuplicateProductException;
import com.wityo.modules.product.exception.EditProductException;
import com.wityo.modules.product.exception.NoProductsFoundException;
import com.wityo.modules.product.exception.ProductAdditionException;
import com.wityo.modules.product.exception.ProductNotFoundException;
import com.wityo.modules.product.exception.ProductStatusChangeException;
import com.wityo.modules.product.exception.UnableToDeleteProductException;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.repository.ProductRepository;
import com.wityo.modules.product.service.ProductService;
import com.wityo.modules.user.repository.CustomerRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	/*
	 * @Description: Method to fetch all products in the database.
	 * 
	 * */
	public List<Product> fetchAllProducts() throws NoProductsFoundException, WityoGenericException{
		try {
			List<Product> products = productRepository.findAll();
			if(products.size() == 0) {
				throw new NoProductsFoundException("");
			}
			return products;
		} catch (Exception e) {
			if(e.getClass().equals(NoProductsFoundException.class)) {
				throw new NoProductsFoundException("No products found in the database, add some products first");
			}
			throw new WityoGenericException("Unable to getch the product list at the moment, please try again");
		}
	}
	
	/*
	 * @Description: Method to fetch a product by product id.
	 * 
	 * */
	public Product getProductById(String productId) throws ProductNotFoundException, WityoGenericException {
		try {
			Optional<Product> oProduct = productRepository.findById(productId);
			if(oProduct.isPresent()) {
				return oProduct.get();
			}
			throw new ProductNotFoundException("");
		} catch (Exception e) {
			if(e.getClass().equals(ProductNotFoundException.class)) {
				throw new ProductNotFoundException("product with product id: "+productId+" not found in records");
			}
			throw new WityoGenericException("Unable to get product by at the moment, try again later.");
		}
	}
	
	/*
	 * @Description: Method to delete a product by product id.
	 * 
	 * */
	public boolean deleteProductById(String productId) throws UnableToDeleteProductException{
		try {
			productRepository.deleteById(productId);
			return true;
		} catch (Exception e) {
			throw new UnableToDeleteProductException("Unable to delete product with product id: "+productId);
		}
	}
	
	/*
	 * @Description: Method to change status of product(isEnabled to true or false).
	 * 
	 * */
	public boolean changeProductStatusById(String productId)throws ProductStatusChangeException {
		try {
			Optional<Product> oProduct = productRepository.findById(productId);
			Product product = oProduct.get();
			if(product.isEnabled()) {
				product.setEnabled(false);
			}
			product.setEnabled(false);
			return product.isEnabled();
		} catch (Exception e) {
			throw new ProductStatusChangeException("Unable to update status of product with product id: "+productId+"., try again");
		}
	}
	
	/*
	 * @Description: Method to fetch ADD a product to records.
	 * 
	 * */
	public Product addProduct(Product product) throws ProductAdditionException, DuplicateProductException {
		try {
			Optional<Product> oProduct = productRepository.findByProductName(product.getProductName());
			if(oProduct.isPresent()) {
				throw new DuplicateProductException("");
			}
			return productRepository.save(product);
		} catch (Exception e) {
			if(e.getClass().equals(DuplicateProductException.class)) {
				throw new DuplicateProductException("The product you're trying to access already exists in the database");
			}
			throw new ProductAdditionException("Unable to add product, please try again after sometime");
		}
	}
	
	/*
	 * @Description: Method to update an existing product.
	 * 
	 * */
	public Product updateProduct(Product latestProduct) throws EditProductException {
		try {
			Optional<Product> oProduct = productRepository.findById(latestProduct.getProductId());
			if(oProduct.isPresent()) {
				return productRepository.save(latestProduct);
			} throw new EditProductException("");
		} catch (Exception e) {
				throw new EditProductException("Unable to update product with id: "+latestProduct.getProductId());
		}
	}
}
