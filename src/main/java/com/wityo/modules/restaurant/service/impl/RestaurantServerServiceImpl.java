package com.wityo.modules.restaurant.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.wityo.modules.restaurant.Exception.NoRestaurantFoundException;
import com.wityo.modules.restaurant.dto.RestaurantListDto;

@Service
public class RestaurantServerServiceImpl {
	
	@Autowired
	WebClient.Builder webClient;
	
	public RestaurantListDto fetchRestaurantListByIdAndName() throws NoRestaurantFoundException{
		try {
			RestaurantListDto restaurantList = webClient
				.build()
				.get()
				.uri("http://localhost:8081/restaurant-list")
				.retrieve()
				.bodyToMono(RestaurantListDto.class)
				.block();
			if(restaurantList.getRestaurantDetails().size() == 0)
				throw new NoRestaurantFoundException("Empty response from server");
			return restaurantList;
		} catch (Exception e) {
			throw new NoRestaurantFoundException(e.getMessage());
		}
		
	}

}