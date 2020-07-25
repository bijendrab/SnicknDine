package com.wityo.modules.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.wityo.common.Constant;
import com.wityo.common.WityoRestAppProperties;
import com.wityo.modules.cart.model.Cart;
import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.repository.CartItemRepository;
import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.TableOrdersResponse;
import com.wityo.modules.order.dto.UpdateOrderItemDTO;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.reservation.service.impl.ReservationServiceImpl;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
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
    RestTemplate restTemplate;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private WityoRestAppProperties wityoRestAppProperties;

    private Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    public CustomerOrder placeCustomerOrder(Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
            Cart cart = customer.getCart();
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
            return response;
        } catch (Exception e) {
        }
        return null;
    }
}
