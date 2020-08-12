package com.wityo.modules.order.controller;

import com.wityo.common.Constant;
import com.wityo.modules.order.dto.EndDiningInfo;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.TableOrdersResponse;
import com.wityo.modules.order.dto.UpdateOrderItemDTO;
import com.wityo.modules.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity<?> placeOrder(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("body", orderService.placeCustomerOrder(restaurantId));
        if (response.get("body")==null){
            response.put("message","either reservation not occurred or something wrong in order request ");
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.put("message","order placed");
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "get order of a table of the user", response = TableOrdersResponse.class)
    @GetMapping("/get-order/{restaurantId}")
    public ResponseEntity<?> getOrdersForTable(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Table Order Items");
        response.put("body", orderService.getCustomerTableOrders(restaurantId));
        if (response.get("body")==null){
            response.put("message","fetching order is not successful. may be no reservation or something wrong with order");
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        else {
            response.put("message","list of orders");
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
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
        response.put("body", orderService.deleteCustomerOrderedItem(orderItem, restaurantId));
        if (response.get("body").equals(Boolean.FALSE)){
            response.put("message","Order Item: " + orderItem.getOrderItemId() + " could not get deleted!");
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.put("message","Order Item: " + orderItem.getOrderItemId() + " got deleted!");
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/end-dining")
    public ResponseEntity<?> endDining(@RequestBody EndDiningInfo endDiningInfo) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("body", orderService.endDining(endDiningInfo));
        if (response.get("body")==null){
            response.put("message","Something went wrong in end dining");
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.put("message","Dining Ended Successfully");
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/getPastOrders/{restaurantId}")
    public ResponseEntity<?> getPastOrders(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("body", orderService.getPastOrders(restaurantId));
        if (response.get("body")==null){
            response.put("message","Something went wrong in getting past orders");
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.put("message","list of past orders");
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
    }

}
