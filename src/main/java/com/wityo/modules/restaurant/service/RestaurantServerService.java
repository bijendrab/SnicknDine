package com.wityo.modules.restaurant.service;

import com.wityo.modules.restaurant.Exception.NoRestaurantFoundException;
import com.wityo.modules.restaurant.dto.RestaurantBasicDTO;
import com.wityo.modules.restaurant.dto.RestaurantListDto;
import com.wityo.modules.restaurant.dto.RestaurantMenuListDto;

public interface RestaurantServerService {
    RestaurantListDto fetchRestaurantListByIdAndName() throws NoRestaurantFoundException;

    RestaurantMenuListDto getRestaurantMenuByRestId(Long restaurantId);

    RestaurantBasicDTO fetchRestaurantDetailsById(Long restaurantId);
}
