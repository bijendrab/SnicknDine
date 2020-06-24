package com.wityo.modules.restaurant.controller;

import com.wityo.common.Constant;
import com.wityo.modules.restaurant.dto.RestaurantListDto;
import com.wityo.modules.restaurant.dto.RestaurantMenuListDto;
import com.wityo.modules.restaurant.service.RestaurantServerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Constant.RESTAURANT_SERVER_API)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestaurantServerController {

    @Autowired
    RestaurantServerService restaurantService;

    @ApiOperation(value = "get list of restaurants", response = RestaurantListDto.class)
    @GetMapping(value = "/get-restaurants")
    public ResponseEntity<?> fetchRestaurants() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Restaurant list by Id and Name");
        response.put("body", restaurantService.fetchRestaurantListByIdAndName());
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.FOUND);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "get menu items of a restaurant", response = RestaurantMenuListDto.class)
    @GetMapping("/menu/{restaurantId}")
    public ResponseEntity<?> fetchRestaurantMenu(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Restaurant menu list");
        response.put("body", restaurantService.getRestaurantMenuByRestId(restaurantId));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.FOUND);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
