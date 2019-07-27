package com.wityo.modules.restaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
			ParameterizedTypeReference<RestaurantListDto> responseType = new ParameterizedTypeReference<RestaurantListDto>() {};
			RestaurantListDto restaurantList = webClient
				.build()
				.get()
				.uri("http://localhost:8081/api/restaurant/restaurant-id-list")
				.retrieve()
				.bodyToMono(responseType)
				.block();
			if(restaurantList.getRestaurantDetails().size() == 0)
				throw new NoRestaurantFoundException("Empty response from server");
			return restaurantList;
		} catch (Exception e) {
			throw new NoRestaurantFoundException(e.getMessage());
		}
		
	}

}
