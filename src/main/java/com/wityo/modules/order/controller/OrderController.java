package com.wityo.modules.order.controller;

import com.wityo.common.Constant;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.UpdateOrderItemDTO;
import com.wityo.modules.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(Constant.ORDER_API)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout/{restaurantId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long restaurantId, @RequestBody PlaceOrderDTO order) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Reservation status");
        response.put("body", orderService.placeCustomerOrder(order, restaurantId));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/get-order/{restaurantId}")
    public ResponseEntity<?> getOrdersForTable(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Table Order Items");
        response.put("body", orderService.getCustomerTableOrders(restaurantId));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/update-ordered-item/{restaurantId}")
    public ResponseEntity<?> updateOrderedItem(@RequestBody UpdateOrderItemDTO orderItem, @PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Order Item: " + orderItem.getOrderItemId() + " updated!");
        response.put("body", orderService.updateCustomerOrderedItem(orderItem, restaurantId));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-ordered-item/{restaurantId}")
    public ResponseEntity<?> deleteOrderedItem(@RequestBody UpdateOrderItemDTO orderItem, @PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Order Item: " + orderItem.getOrderItemId() + " deleted!");
        response.put("body", orderService.deleteCustomerOrderedItem(orderItem, restaurantId));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
