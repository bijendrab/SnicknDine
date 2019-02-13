package com.dao;

import com.model.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CustomerOrderDaoImpl implements CustomerOrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addCustomerOrder(CustomerOrder customerOrder) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(customerOrder);
        session.flush();
        session.close();
    }

    public List<Cart> getCustomerOrderByCustomerId() {
        List<Cart> cart=null;
        Session session = sessionFactory.openSession();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailId = user.getUsername();
        if (user.getAuthorities().iterator().next().toString().equals("ROLE_ADMIN")) {
            cart = session.createCriteria(Cart.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            System.out.println(cart);
        }
        else if(user.getAuthorities().iterator().next().toString().equals("ROLE_USER")){
            Query query = session.createQuery("from User where emailId=?");
            query.setString(0, emailId);
            User users = (User) query.uniqueResult();
            Customer customer = users.getCustomer();
            System.out.println(customer.getCustomerId());
            cart = session.createCriteria(Cart.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).add(Restrictions.eq("customer.customerId",customer.getCustomerId())).list();
            System.out.println(cart.size());
        }
        session.flush();
        session.close();
        return cart;
    }
}
