package com.dao;

import java.util.*;

import com.model.ProductQuantityOptions;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model.Product;



@Repository(value = "productDao")
public class ProductDaoImpl implements ProductDao {

	// this class is wired with the sessionFactory to do some operation in the
	// database

	@Autowired
	private SessionFactory sessionFactory;

	// this will create one sessionFactory for this class
	// there is only one sessionFactory should be created for the applications
	// we can create multiple sessions for a sessionFactory
	// each session can do some functions

	public List<Product> getAllProducts() {
		Session session = sessionFactory.openSession();
		List<Product> products = session.createQuery("from Product").list();
		session.flush();
		session.close();
		return products;
	}
	public Product getProductById(int productId) {
		Session session = sessionFactory.openSession();
		Product product = (Product) session.get(Product.class, productId);
		session.close();
		return product;
	}

	public void deleteProduct(int productId) {
		Session session = sessionFactory.openSession();
		Product product = (Product) session.get(Product.class, productId);
		session.delete(product);
		session.flush();
		session.close();
	}
	public void setProductStatus(int productId){
		Session session = sessionFactory.openSession();
		Product product = (Product) session.get(Product.class, productId);
		if(product.getIsEnabled().equals(true)){
			product.setIsEnabled(false);
		}
		else{
			product.setIsEnabled(true);
		}
		session.update(product);
		session.flush();
		session.close();
	}


	public void addProduct(Product product) {
		Session session = sessionFactory.openSession();
		setProduct(product);
		session.save(product);
		session.flush();
		session.close();
	}

	public void editProduct(Product product) {
		Session session = sessionFactory.openSession();
		setProduct(product);
		session.merge(product);
		session.flush();
		session.close();
	}

	public void setProduct(Product product) {
		Set<ProductQuantityOptions> pqo = new HashSet<ProductQuantityOptions>();
		for (ProductQuantityOptions potion : product.getQuantityOption()) {
			potion.setProduct(product);
			pqo.add(potion);
		}
	}




	/*String getQuery="SELECT a.productId as productId, a.category as category, a.subCategory as subCategory," +
				"a.cuisine as cuisine, a.description as description, a.name as name, a.isAdd as isAdd , a.selectedQuantity as selectedQuantity, " +
               "CONCAT('[',GROUP_CONCAT('{\"option\":\"', b.option, '\", \"price\":',b.price,',  \"quantity\":',b.quantity,'}'),']') as quantityOption " +
				"FROM Product as a join  a.quantityOption as b GROUP BY a.productId";
		String getQuery="FROM Product as a GROUP BY a.productId";
		Query query = session.createQuery(getQuery).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		Query query = session.createQuery(getQuery).setResultTransformer(Transformers.aliasToBean(Product.class));
		List<Product> products=query.list();*/

}
