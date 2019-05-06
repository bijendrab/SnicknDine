package com.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		String getQuery="SELECT a.productId as productId, a.category as category, a.subCategory as subCategory," +
				"a.cuisine as cuisine, a.description as description, a.name as name, a.isAdd as isAdd , a.selectedQuantity as selectedQuantity, " +
               "CONCAT('[',GROUP_CONCAT('{option:', b.option, ', price:',b.price,',  quantity:',b.quantity,'}'),']') as quantityOption " +
				"FROM Product as a join  a.quantityOption as b GROUP BY a.productId";
		Query query = session.createQuery(getQuery).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Product> products = query.list();
		System.out.println(products);
		session.flush();
		// it will close the particular session after completing the process
		session.close();
		return products;
	}

	public Product getProductById(int productId) {

		// Reading the records from the table
		Session session = sessionFactory.openSession();
		// select * from Product where isbn=i
		Product product = (Product) session.get(Product.class, productId);
		session.close();
		return product;
	}

	public void deleteProduct(int productId) {
		Session session = sessionFactory.openSession();
		Product product = (Product) session.get(Product.class, productId);
		session.delete(product);
		session.flush();
		session.close();// close the session
	}

	public void disableProduct(int productId) {
		setProductStatus(productId,false);
	}
	public void enableProduct(int productId) {
		setProductStatus(productId,true);
	}
	private void setProductStatus(int productId, boolean status){
		Session session = sessionFactory.openSession();
		Product product = (Product) session.get(Product.class, productId);
		product.setIsAdd(status);
		session.update(product);
		session.flush();
		session.close();// close the session
	}


	public void addProduct(Product product) {
		Session session = sessionFactory.openSession();
		Set<ProductQuantityOptions> pqo= new HashSet<ProductQuantityOptions>();
		for (ProductQuantityOptions potion:product.getQuantityOption()){
			potion.setProduct(product);
			pqo.add(potion);
		}
		session.save(product);
		session.close();
	}

	public void editProduct(Product product) {
		Session session = sessionFactory.openSession();
		session.update(product);
		session.flush();
		session.close();
	}

}
