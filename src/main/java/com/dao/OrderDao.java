package com.dao;

import com.dataTransferObjects.OrderRequestDTO;
import com.model.*;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Reservation findReservationByOrder(Order order);

    Order saveOrderForReservation(Reservation res, Order newOrder);

    Order getByOrderId(int orderId);

    Order changeOrderStatus(Order order, OrderStatus status) throws Exception;

    Order confirmOrder(int orderId) throws Exception;

    Order addItemToOrder(Order order, Product item, int itemOrderedNumber) throws Exception;

    Order findOrderByReservationInfo(Reservation res);

    Order processOrderRequest(OrderRequestDTO orderReq) throws Exception;

    float calculateTotalCost(Order order) throws Exception;

    List<Order> getAllOrders();

    List<Order> getOrdersWithStatus(OrderStatus orderStatus);

    List<Order> findAllReservationsByCustomers(Customer cs);
}
