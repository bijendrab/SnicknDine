package com.wityo.modules.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	
	public Product findByProductId(String productId);
	public Optional<Product> findByProductName(String productName);

}
