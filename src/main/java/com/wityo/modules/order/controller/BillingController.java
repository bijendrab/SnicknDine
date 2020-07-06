package com.wityo.modules.order.controller;

import java.util.HashMap;
import java.util.Map;
import com.wityo.common.Constant;
import com.wityo.modules.order.dto.BillingDetailResponse;
import com.wityo.modules.order.service.BillingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(Constant.ORDER_API)
public class BillingController {
    @Autowired
    private BillingService billingService;


    @ApiOperation(value = "get billing of table", response = BillingDetailResponse.class)
    @GetMapping("/billing/{restaurantId}")
    public ResponseEntity<?> fetchRestaurantMenu(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Get Bill of the Table Order");
        response.put("body", billingService.getOrderBill(restaurantId));
        if (response.get("body")==null){
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
    }

}
