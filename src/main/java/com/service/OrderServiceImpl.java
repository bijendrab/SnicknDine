package com.service;

import com.dao.OrderDao;
import com.dataTransferObjects.ImmediateRequestDTO;
import com.dataTransferObjects.OrderRequestDTO;
import com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value="OrderService")
public class OrderServiceImpl implements  OrderService {
    @Autowired
    private OrderDao orderDao;

    public Reservation findReservationByOrder(Order order){
        return orderDao.findReservationByOrder(order);

    }

    public Order saveOrderForReservation(Reservation res, Order newOrder){
        return orderDao.saveOrderForReservation(res,newOrder);
    }

    public Order getByOrderId(int orderId){
        return orderDao.getByOrderId(orderId);
    }

    public Order changeOrderStatus(Order order, OrderStatus status) throws Exception{
        return orderDao.changeOrderStatus(order, status);

    }

    public Order confirmOrder(int orderId) throws Exception {
        return orderDao.confirmOrder(orderId);
    }

    public Order addItemToOrder(Order order, Product item, int itemOrderedNumber) throws Exception{
        return orderDao.addItemToOrder(order,item,itemOrderedNumber);
    }

    public Order findOrderByReservationInfo(Reservation res){
        return orderDao.findOrderByReservationInfo(res);
    }

    public Order processOrderRequest(int cartId,int custId,ImmediateRequestDTO immediateReq) throws Exception{
        return orderDao.processOrderRequest(cartId,custId,immediateReq);
    }

    public float calculateTotalCost(Order order) throws Exception{
        return orderDao.calculateTotalCost(order);
    }

    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }

    public List<Order> getOrdersWithStatus(OrderStatus orderStatus) {
        return orderDao.getOrdersWithStatus(orderStatus);
    }

    public List<Order> findAllReservationsByCustomers(Customer cs) {
        return orderDao.findAllReservationsByCustomers(cs);
    }
}
