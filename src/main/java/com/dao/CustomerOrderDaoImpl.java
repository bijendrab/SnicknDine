package com.dao;

import com.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public List<Map<Integer, List<OrderItem>>> getCustomerOrderByCustomerId() {
        List<Map<Integer, List<OrderItem>>> orderItems = new ArrayList<>();
        Session session = sessionFactory.openSession();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailId = user.getUsername();
        if (user.getAuthorities().iterator().next().toString().equals("ROLE_ADMIN")) {
           /* orderItems = session.createCriteria(OrderItem.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            setOrderWaitTime(orderItems);
            System.out.println(orderItems);*/
        } else if (user.getAuthorities().iterator().next().toString().equals("ROLE_USER")) {
            Customer customer = getCustomer(session, emailId);
            System.out.println(customer.getCustomerId());

            String hql = "SELECT Res.relatedTable.id FROM Reservation AS Res " +
                    "WHERE Res.relatedCustomer.id = :customerId";

            Integer tableId = (Integer) session.createQuery(hql).setParameter("customerId", customer.getCustomerId()).uniqueResult();

            String hql1 = "from Order Ord JOIN Ord.accordingReservation Res " +
                    "with Res.relatedTable.id=:tableId";

            Iterator query = session.createQuery(hql1).setParameter("tableId", tableId).list().iterator();
            Map<Integer, List<OrderItem>> orderHash = new HashMap<>();
            List<OrderItem> oderList1 = new ArrayList<>();
            List<OrderItem> oderList2 = new ArrayList<>();
            while (query.hasNext()) {
                Object[] row = (Object[]) query.next();
                Order ord = (Order) row[0];


                for (OrderItem ordItem : ord.getMenuItemOrders()) {
                    if (ord.getAccordingReservation().getRelatedCustomer().getCustomerId() == customer.getCustomerId()) {
                        oderList1.add(ordItem);
                    } else {
                        oderList2.add(ordItem);
                    }
                }

            }
            if (!oderList1.isEmpty()) {
                orderHash.put(1, oderList1);
                setOrderWaitTime(oderList1);
            }
            if (!oderList2.isEmpty()) {
                orderHash.put(0, oderList2);
                setOrderWaitTime(oderList2);
            }
            orderItems.add(orderHash);

        }
        session.flush();
        session.close();
        return orderItems;
    }

    public void setOrderWaitTime(List<OrderItem> orderItems) {
        for (OrderItem orderitem:orderItems) {
            long diff = new Date().getTime() - orderitem.getOrderCreationTime().getTime();
            long diffMinutes = diff / (60 * 1000) % 60;
            orderitem.setWaitTime(diffMinutes);
        }
    }

    public Customer getCustomer(Session session, String emailId) {
        Query query = session.createQuery("from User where emailId=?");
        query.setString(0, emailId);
        User users = (User) query.uniqueResult();
        return users.getCustomer();
    }

    public void updateCustomerOrderItem(OrderItem orderitem) {
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
