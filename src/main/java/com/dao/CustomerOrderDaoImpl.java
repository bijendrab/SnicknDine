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

import java.util.Date;
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

    public List<OrderItem> getCustomerOrderByCustomerId() {
        List<OrderItem> orderItems=null;
        Session session = sessionFactory.openSession();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailId = user.getUsername();
        if (user.getAuthorities().iterator().next().toString().equals("ROLE_ADMIN")) {
            orderItems = session.createCriteria(OrderItem.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            setOrderWaitTime(orderItems);
            System.out.println(orderItems);
        }
        else if(user.getAuthorities().iterator().next().toString().equals("ROLE_USER")){
            Customer customer = getCustomer(session, emailId);
            System.out.println(customer.getCustomerId());
            Criteria criteria  = session.createCriteria(OrderItem.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.createAlias("cart", "cart");
            criteria.add(Restrictions.eq("cart.cartId",customer.getCustomerId()));
            orderItems = criteria.list();
            setOrderWaitTime(orderItems);
            System.out.println(orderItems.size());

        }
        session.flush();
        session.close();
        return orderItems;
    }

    public void setOrderWaitTime(List<OrderItem> orderItems) {
        for (int i = 0; i < orderItems.size(); i++) {
            long diff = new Date().getTime() - orderItems.get(i).getOrderCreationTime().getTime();
            long diffMinutes = diff / (60 * 1000) % 60;
            orderItems.get(i).setWaitTime(diffMinutes);
        }
    }

    public Customer getCustomer(Session session, String emailId) {
        Query query = session.createQuery("from User where emailId=?");
        query.setString(0, emailId);
        User users = (User) query.uniqueResult();
        return users.getCustomer();
    }

    public void updateCustomerOrderItem(OrderItem orderitem){
        Session session = sessionFactory.openSession();
        System.out.println(orderitem.getStatus());
        //OrderItem orderit = (OrderItem) session.get(OrderItem.class, orderitem.getProduct().getProductId());
        orderitem.setStatus("processed");
        //System.out.println(orderit.getCart().getCartId());
        session.saveOrUpdate(orderitem);
        session.flush();
        session.close();

    }
}
