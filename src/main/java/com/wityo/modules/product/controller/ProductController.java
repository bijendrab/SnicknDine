package com.wityo.modules.product.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.common.Constant;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.service.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping(Constant.PRODUCT_API)
public class ProductController {
	
	@Autowired
	ProductService productServiceImpl;

	ResponseEntity<?> productResponseBuilder(String msg, Object body, boolean error, HttpStatus status){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", msg);
		response.put("body", body);
		response.put("error", error);
		response.put("status", status);
		if(error)
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallproducts")
	public ResponseEntity<?> getAllProducts(){
		try {
			return productResponseBuilder(
					"List of products",
					productServiceImpl.fetchAllProducts(),
					false,
					HttpStatus.FOUND);	
		} catch (Exception e) {
			return productResponseBuilder(
					e.getMessage(),
					"",
					true,
					HttpStatus.OK);
			}
	}
	
	@GetMapping("/get/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable String productId){
		try {
			return productResponseBuilder(
					"Product",
					productServiceImpl.getProductById(productId),
					false,
					HttpStatus.FOUND);	
		} catch (Exception e) {
			return productResponseBuilder(
					e.getMessage(),
					"",
					true,
					HttpStatus.OK);
			}
	}
	
	@PostMapping("/addnew")
	public ResponseEntity<?> addProduct(@RequestBody Product product){
		try {
			return productResponseBuilder(
					"Product added successfully.",
					productServiceImpl.addProduct(product),
					false,
					HttpStatus.FOUND);	
		} catch (Exception e) {
			return productResponseBuilder(
					e.getMessage(),
					"",
					true,
					HttpStatus.OK);
			}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody Product product){
		try {
			return productResponseBuilder(
					"Product updated!",
					productServiceImpl.updateProduct(product),
					false,
					HttpStatus.FOUND);	
		} catch (Exception e) {
			return productResponseBuilder(
					e.getMessage(),
					"",
					true,
					HttpStatus.OK);
			}
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable String productId){
		try {
			return productResponseBuilder(
					"Product deleted.",
					productServiceImpl.deleteProductById(productId),
					false,
					HttpStatus.OK);	
		} catch (Exception e) {
			return productResponseBuilder(
					e.getMessage(),
					"",
					true,
					HttpStatus.OK);
			}
	}
	
	@PutMapping("/updatestatus/{productId}")
	public ResponseEntity<?> updateProductStatus(@PathVariable String productId){
		try {
			return productResponseBuilder(
					"Product status updated.",
					productServiceImpl.changeProductStatusById(productId),
					false,
					HttpStatus.OK);	
		} catch (Exception e) {
			return productResponseBuilder(
					e.getMessage(),
					"",
					true,
					HttpStatus.OK);
			}
	}
}
