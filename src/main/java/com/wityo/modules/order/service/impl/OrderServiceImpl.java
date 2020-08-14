package com.wityo.modules.order.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wityo.common.WityoRestAppProperties;
import com.wityo.modules.Binding.service.UserRestBindService;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.order.Repository.OrderHistoryRepository;
import com.wityo.modules.order.dto.BillingDetailResponse;
import com.wityo.modules.order.dto.EndDiningInfo;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.TableOrdersResponse;
import com.wityo.modules.order.dto.UpdateOrderItemDTO;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.model.OrderHistory;
import com.wityo.modules.order.service.BillingService;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.reservation.service.impl.ReservationServiceImpl;
import com.wityo.modules.restaurant.dto.RestaurantBasicDTO;
import com.wityo.modules.restaurant.service.RestaurantServerService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private WityoRestAppProperties wityoRestAppProperties;

    @Autowired
    UserRestBindService userRestBindService;

    @Autowired
    BillingService billingService;

    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    @Autowired
    RestaurantServerService restaurantServerService;

    private Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    public CustomerOrder placeCustomerOrder(Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
            Cart cart = customer.getCart();
            cart.setCartItems(cart.getCartItems().stream().filter(item -> item.getRestaurantId() == restaurantId).collect(Collectors.toSet()));
            List<CartItem> targetCart = new ArrayList<>(cart.getCartItems());
            placeOrderDTO.setCartItems(targetCart);
            placeOrderDTO.setCustomer(customer);
            CustomerOrder placedOrder = restTemplate.postForObject(
                wityoRestAppProperties.getWityoUserAppUrl() + "api/customerOrder/checkout/" + restaurantId, placeOrderDTO,
                    CustomerOrder.class);
            if (placedOrder != null) {
                for(CartItem cartItem:placeOrderDTO.getCartItems()){
                    cartItemRepository.deleteById(cartItem.getCartItemId());
                }
                userRestBindService.unBindUserToRestaurantCart(restaurantId);
                userRestBindService.bindUserToRestaurantOrder(restaurantId);
                return placedOrder;
            }
        } catch (Exception e) {
            logger.error("Exception in reserveTable inside ReservationServiceImpl:- {}", e);
        }
        return null;
    }


    public TableOrdersResponse getCustomerTableOrders(Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            TableOrdersResponse response = restTemplate.postForObject(
                wityoRestAppProperties.getWityoUserAppUrl() + "api/customerOrder/getTableOrder/" + restaurantId, customer,
                    TableOrdersResponse.class);
            return response;
        } catch (Exception e) {
            logger.error("Exception in reserveTable inside ReservationServiceImpl:- {}", e);
        }
        return null;
    }

    public CustomerOrder updateCustomerOrderedItem(UpdateOrderItemDTO dto, Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            dto.setCustomer(customer);

            String url = wityoRestAppProperties.getWityoUserAppUrl() + "api/customerOrder/updateUserOrderedItem/" + restaurantId;
            HttpEntity<UpdateOrderItemDTO> entity = new HttpEntity<UpdateOrderItemDTO>(dto);
            CustomerOrder response = restTemplate.exchange(url,
                    HttpMethod.PUT,
                    entity,
                    CustomerOrder.class)
                    .getBody();
            return response;

        } catch (Exception e) {
        }
        return null;
    }

    public Boolean deleteCustomerOrderedItem(UpdateOrderItemDTO dto, Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            dto.setCustomer(customer);
            String url = wityoRestAppProperties.getWityoUserAppUrl() + "api/customerOrder/deleteUserOrderedItem/" + restaurantId;
            HttpEntity<UpdateOrderItemDTO> entity = new HttpEntity<UpdateOrderItemDTO>(dto);
            Boolean response = restTemplate.exchange(url,
                    HttpMethod.DELETE,
                    entity,
                    Boolean.class)
                    .getBody();
            TableOrdersResponse tableOrdersResponse = getCustomerTableOrders(restaurantId);
            if(tableOrdersResponse.getTableOrders() == null){
                userRestBindService.unBindUserToRestaurantOrder(restaurantId);
            }
            return response;
        } catch (Exception e) {
            logger.error("Exception in Delete Order Exception:- {}", e.getMessage());
        }
        return null;
    }

    public Boolean endDining(EndDiningInfo endDiningInfo) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TableOrdersResponse tableOrdersResponse = getCustomerTableOrders(endDiningInfo.getRestId());
            BillingDetailResponse billingDetailResponse = billingService.getOrderBill(endDiningInfo.getRestId());
            Set<Long> customerIds = new HashSet<>();
            Long restaurantId = null;
            String restaurantName = null;
            Long tableId = null;
            int count = 0;
            for(CustomerOrder customerOrder:tableOrdersResponse.getTableOrders()){
                Customer customer = new Gson().fromJson(customerOrder.getAccordingReservation().getCustomerInfo(), Customer.class);
                if(count==0) {
                    restaurantId = customerOrder.getAccordingReservation().getRelatedTable().getRestId();
                    RestaurantBasicDTO restaurantBasicDTO = restaurantServerService.fetchRestaurantDetailsById(restaurantId);
                    restaurantName = restaurantBasicDTO.getRestName();
                    tableId = customerOrder.getAccordingReservation().getRelatedTable().getId();
                }
                count ++;
                if(customerOrder.getOrderedBy().equals("restaurant")){
                    continue;
                }
                customerIds.add(customer.getCustomerId());
            }
            HttpEntity<EndDiningInfo> entity = new HttpEntity<>(endDiningInfo);
            /**
             * Save Order to Restaurant
             */
            Boolean endDiningStatus = restTemplate.exchange(
                wityoRestAppProperties.getWityoUserAppUrl() + "api/customerOrder/end-dining", HttpMethod.DELETE, entity,
                Boolean.class).getBody();
            /**
             * Save Order to Customer
             */
            for(Long customerId:customerIds){
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setOrders(new ObjectMapper().writeValueAsString(tableOrdersResponse));
                orderHistory.setRestaurantId(restaurantId);
                orderHistory.setRestaurantName(restaurantName);
                orderHistory.setTableId(tableId);
                orderHistory.setPaymentStatus(Boolean.TRUE);
                orderHistory.setPaymentMethod("CASH");
                orderHistory.setOrderCreationTime(tableOrdersResponse.getTableOrders().get(0).getMenuItemOrders().iterator().next().getOrderCreationTime());
                orderHistory.setOrderHistoryTime(LocalDateTime.now());
                orderHistory.setTotalCost(billingDetailResponse.getTotalCost());
                orderHistory.setCustomerId(customerId);
                orderHistoryRepository.save(orderHistory);
            }
            /**
             * UnBind User from Restaurant
             */
            userRestBindService.unBindUserToRestaurantOrder(endDiningInfo.getRestId());
            for(Long customerId:customerIds){
                User userDetails = userRepository.findByCustomerCustomerId(customerId);
                userRestBindService.unBindOtherUsersToRestaurantOrder(userDetails.getUserId() , endDiningInfo.getRestId() );
            }
            return endDiningStatus;
        } catch (Exception e) {
            {
                logger.error("Exception in end dining:- {}", e.getMessage());
                return false;
            }
        }
    }

    public List<OrderHistory> getPastOrders(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            return orderHistoryRepository.findAllByCustomerId(customer.getCustomerId());
        }
        catch (Exception e) {
            logger.error("Exception in getting past orders- {}", e.getMessage());
        }
        return null;
    }
}
