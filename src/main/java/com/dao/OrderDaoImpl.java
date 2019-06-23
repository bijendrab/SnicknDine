package com.dao;

import com.dataTransferObjects.OrderRequestDTO;
import com.model.*;
import com.service.ReservationService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository(value = "OrderDao")
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private ReservationDao resRepo;
    @Autowired
    private CartDao cartdao;
    @Autowired
    private ProductDao myRestaurantMenu;
    @Autowired
    private SessionFactory sessionFactory;


    public Order processOrderRequest(int cartId,int custId) throws Exception {

        Reservation accordingReservation = resRepo.getByCustomerId(custId);

        if (accordingReservation == null) {
            // TODO: throw not valid reservation
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Order newOrder = new Order();

        newOrder.setAccordingReservation(accordingReservation);
        newOrder.setStatus(OrderStatus.ON_HOLD);
        Cart cart =cartdao.getCartByCartId(cartId);
        float totalPrice = 0;
        Date creationDate = new Date();
        for (CartItem mi : cart.getCartItem()) {
            totalPrice += mi.getPrice();

            OrderItem menuItem_Order = new OrderItem();
            menuItem_Order.setQuantity(mi.getQuantity());
            menuItem_Order.setItemName(mi.getItemName());
            menuItem_Order.setPrice(mi.getPrice());
            menuItem_Order.setOrderCreationTime(creationDate);
            menuItem_Order.setStatus("Unprocessed");
            menuItem_Order.setQuantityOption(mi.getQuantityOption());
            menuItem_Order.setProduct(mi.getProduct());
            menuItem_Order.setCart(mi.getCart());

            newOrder.getMenuItemOrders().add(menuItem_Order);
        }

        newOrder.setTotalCost(totalPrice);
        setOrder(newOrder);
        session.merge(newOrder);
        session.getTransaction().commit();
        session.close();
        removeFromCart(cart);

        return newOrder;
    }

    public void setOrder(Order order) {
        Set<OrderItem> pqo = new HashSet<>();
        for (OrderItem potion : order.getMenuItemOrders()) {
            potion.setOrder(order);
            pqo.add(potion);
        }
    }
    public void removeFromCart(Cart cart) {
        List<CartItem> cartItems = cart.getCartItem();
        for (CartItem cartItem : cartItems) {
            Session sessionDelete = sessionFactory.openSession();
            CartItem cartItemDelete = (CartItem) sessionDelete.get(CartItem.class, cartItem.getCartItemId());
            Query query = sessionDelete.createQuery("delete CartItem where cartItemId = :cartItemId");
            query.setParameter("cartItemId", cartItem.getCartItemId());
            int result = query.executeUpdate();
            if (result >= 0) {
                System.out.println("cart Items are removed");
            }
            Cart cart1 = cartItemDelete.getCart();
            List<CartItem> cartItems1 = cart1.getCartItem();
            cartItems1.remove(cartItemDelete);
            sessionDelete.flush();
            sessionDelete.close();
        }
    }

    public Order createNewOrder
            (Reservation res,
             HashMap<Product, Integer> orderedItems) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.PREPARING);
        newOrder.setAccordingReservation(res);
        newOrder = saveOrder(newOrder).get();


        float totalCost = 0;
       /* Set<MenuItem> itemSet = orderedItems.keySet();
        for (MenuItem item : itemSet) {
            MenuItemOrder menuItemOrder = new MenuItemOrder();
            MenuItemOrderId id = new MenuItemOrderId();
            int number = orderedItems.get(item);

            id.setMenuItem(item);
            id.setOrder(newOrder);

            menuItemOrder.setPk(id);
            menuItemOrder.setNumber(number);

            totalCost += number * item.getPrice();
            session.save(menuItemOrder);

            newOrder.getMenuItemOrders().add(menuItemOrder);
        }*/
        newOrder.setTotalCost(totalCost);

        session.merge(newOrder);
        session.save(newOrder);
        session.getTransaction().commit();

        session.close();
        return newOrder;
    }

    @SuppressWarnings("Duplicates")
    public Optional<Order> saveOrder(Order order) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();

        return Optional.of(order);
    }

    @SuppressWarnings("Duplicates")
    public OrderItem saveMenuItemOrder(OrderItem menuItemOrder) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(menuItemOrder);
        session.getTransaction().commit();

        return menuItemOrder;
    }

    @Override
    public Reservation findReservationByOrder
            (Order order) {
        return order.getAccordingReservation();
    }

    @Override
    public Order saveOrderForReservation
            (Reservation res, Order newOrder) {

        //TODO
        return this.createNewOrder(res, null);
    }

    @Override
    public Order changeOrderStatus
            (Order order, OrderStatus status)
            throws
            Exception {

        Order registeredOrder = this.getByOrderId(order.getOrderId());

        /*if (registeredOrder.isPresent()) {
            registeredOrder.get().setStatus(status);

            if (status.equals(OrderStatus.CONFIRMED)) {   *//* if the order is confirmed then calculate its cost *//*
                this.calculateTotalCost(order);
            }

            return registeredOrder;
        } else {
            System.err.println("oops orderId is not valid");
            return null;
        }*/
        return registeredOrder;
    }

    @Override
    public Order getByOrderId
            (int orderId) {
        Session session = sessionFactory.openSession();
        Order order = (Order) session.get(Order.class, orderId);
        session.close();

        return order;
    }

    @Override
    @Deprecated
    public Order confirmOrder(int orderId)
            throws
            Exception {

        Order order = this.getByOrderId(orderId);

        /*if (order.isPresent()) {
            this.changeOrderStatus(order.get(), OrderStatus.CONFIRMED);
            return Optional.ofNullable(order);
        } else {
            return null;
        }*/
        return  order;
    }

    @Override
    public Order addItemToOrder
            (Order order, Product item, int itemOrderedNumber)
            throws
            Exception {

        if (order.getMenuItemOrders() == null) {
            order.setMenuItemOrders(new LinkedHashSet<>());
        }


      /*  MenuItemOrder menuItemOrder = new MenuItemOrder();
        menuItemOrder.getPk().setMenuItem(item);
        menuItemOrder.getPk().setOrder(order);
        menuItemOrder.setNumber(itemOrderedNumber);
        saveMenuItemOrder(menuItemOrder);

        order.getMenuItemOrders().add(menuItemOrder);

        this.calculateTotalCost(order);*/

        return order;
    }

    @Override
    public float calculateTotalCost
            (Order order)
            throws
            Exception {

        float totalCost = 0;
       /* Set<MenuItemOrder> orderedItems = order.getMenuItemOrders();

        for (MenuItemOrder itm : orderedItems) {
            Optional<MenuItem> menuItem = myRestaurantMenu.findMenuItemByName(itm.getPk().getMenuItem().getName());
            if (menuItem.isPresent()) {
                totalCost += menuItem.get().getPrice() * itm.getNumber(); // item price * item count
            }
        }

        order.setTotalCost(totalCost);*/

        return totalCost;
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = sessionFactory.openSession();

        @SuppressWarnings("unchecked")
        List<Order> orders = session.createQuery("FROM Order").list();
        System.out.println("Found " + orders.size() + " Customers");

        session.close();

        return orders;
    }


    @Override
    public List<Order> getOrdersWithStatus
            (OrderStatus orderStatus) {

        Session session = sessionFactory.openSession();
        String query = "FROM Order AS ord WHERE ord.status = :status";

        @SuppressWarnings("unchecked")
        List<Order> orders = session.createQuery(query).setParameter("status", orderStatus).list();
        System.out.println("Found " + orders.size() + " Orders");

        session.close();

        return orders;
    }

    //TODO implement
    @Override
    public Order findOrderByReservationInfo(Reservation res) {
        return null;
    }


    //TODO implement
    @Override
    public ArrayList<Order> findAllReservationsByCustomers(Customer cs) {
        return null;
    }
}
