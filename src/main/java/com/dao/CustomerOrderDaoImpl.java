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
        OrderItem orderOld = (OrderItem) session.get(OrderItem.class, orderitem.getOrderItemId());
        int OrderId=orderOld.getOrder().getOrderId();
        float oldPrice= (float)orderOld.getPrice ();
        float newPrice=0;
        for(ProductQuantityOptions qOps:orderOld.getProduct().getQuantityOption()){
            if(orderitem.getQuantityOption().equals(qOps.getOption())){
                newPrice= (float)(orderitem.getQuantity()*qOps.getPrice());
                break;
            }
        }
        orderitem.setPrice(newPrice);
        orderitem.setOrderCreationTime(orderOld.getOrderCreationTime());
        orderitem.setWaitTime(orderOld.getWaitTime());
        orderitem.setStatus(orderOld.getStatus());
        orderitem.setItemName(orderOld.getItemName());
        orderitem.setImmediateStatus(orderOld.getImmediateStatus());
        orderitem.setCart(orderOld.getCart());
        orderitem.setProduct(orderOld.getProduct());
        orderitem.setOrder(orderOld.getOrder());
        session.merge(orderitem);
        Order order = (Order) session.get(Order.class, OrderId);
        order.setTotalCost(order.getTotalCost()-oldPrice + newPrice);
        session.merge(order);
        session.flush();
        session.close();

    }
    public void deleteCustomerOrderItem(OrderItem orderitem) {
        Session sessionDelete1 = sessionFactory.openSession();
        OrderItem orderOld = (OrderItem) sessionDelete1.get(OrderItem.class, orderitem.getOrderItemId());
        int OrderId=orderOld.getOrder().getOrderId();
        float oldPrice= (float)orderOld.getPrice ();
        Order order = (Order) sessionDelete1.get(Order.class, OrderId);
        float newPrice=order.getTotalCost()-oldPrice;
        Query query = sessionDelete1.createQuery("delete OrderItem where orderItemId = :orderItemId");
        query.setParameter("orderItemId", orderOld.getOrderItemId());
        Query query1 = sessionDelete1.createQuery("update Order set totalCost= :newCost where orderId = :orderId");
        query1.setParameter("orderId", order.getOrderId());
        query1.setParameter("newCost",newPrice );
        int result = query.executeUpdate();
        int result1 = query1.executeUpdate();
        if (result >= 0) {
            System.out.println("Order Item is removed");
        }
        if (result1 >= 0) {
            System.out.println("Order Item is removed");
        }
        sessionDelete1.flush();
        sessionDelete1.close();

    }
}
