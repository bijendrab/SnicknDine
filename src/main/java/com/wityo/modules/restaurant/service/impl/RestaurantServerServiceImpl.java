package com.wityo.modules.restaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wityo.common.Constant;
import com.wityo.modules.restaurant.Exception.NoRestaurantFoundException;
import com.wityo.modules.restaurant.dto.RestaurantListDto;
import com.wityo.modules.restaurant.dto.RestaurantMenuListDto;
import com.wityo.modules.restaurant.service.RestaurantServerService;

@Service
public class RestaurantServerServiceImpl implements RestaurantServerService{
	
//	@Autowired
//	WebClient.Builder webClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestaurantListDto fetchRestaurantListByIdAndName() throws NoRestaurantFoundException{
		try {
//			ParameterizedTypeReference<RestaurantListDto> responseType = new ParameterizedTypeReference<RestaurantListDto>() {};
//			RestaurantListDto restaurantList = webClient
//				.build()
//				.get()
//				.uri("http://localhost:8081/api/restaurant/restaurant-id-list")
//				.retrieve()
//				.bodyToMono(RestaurantListDto.class)
//				.block();
//			if(restaurantList.getRestaurantDetails().size() == 0)
//				throw new NoRestaurantFoundException("Empty response from server");
//			return restaurantList;
			return restTemplate.getForObject
			("http://localhost:8081/api/restaurant/restaurant-id-list", RestaurantListDto.class);
		} catch (Exception e) {
			throw new NoRestaurantFoundException(e.getMessage());
		}
	}
	
	public RestaurantMenuListDto getRestaurantMenuByRestId(Long restaurantId) {
		try {
			RestaurantMenuListDto menuList = restTemplate.getForObject
			(Constant.RESTAURANT_SERVER_URL+"api/menu/"+restaurantId, RestaurantMenuListDto.class);
			return menuList;
		} catch (Exception e) {
			throw new NoRestaurantFoundException(e.getMessage());
		}
	}
}
