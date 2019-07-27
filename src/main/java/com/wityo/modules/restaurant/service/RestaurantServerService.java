package com.wityo.modules.restaurant.service;

import com.wityo.modules.restaurant.Exception.NoRestaurantFoundException;
import com.wityo.modules.restaurant.dto.RestaurantListDto;

public interface RestaurantServerService {
	public RestaurantListDto fetchRestaurantListByIdAndName() throws NoRestaurantFoundException;
}
