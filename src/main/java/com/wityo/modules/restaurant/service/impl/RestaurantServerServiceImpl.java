package com.wityo.modules.restaurant.service.impl;

import com.wityo.common.Constant;
import com.wityo.modules.restaurant.Exception.NoRestaurantFoundException;
import com.wityo.modules.restaurant.dto.RestaurantListDto;
import com.wityo.modules.restaurant.dto.RestaurantMenuListDto;
import com.wityo.modules.restaurant.service.RestaurantServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestaurantServerServiceImpl implements RestaurantServerService {

//	@Autowired
//	WebClient.Builder webClient;

    @Autowired
    private RestTemplate restTemplate;


    /*=================================RESTAURANT LIST REST CALLS================================================*/
    public RestaurantListDto fetchRestaurantListByIdAndName() throws NoRestaurantFoundException {
        try {
            return restTemplate.getForObject
                    ("http://localhost:8081/api/restaurant/restaurant-id-list", RestaurantListDto.class);
        } catch (Exception e) {
            throw new NoRestaurantFoundException(e.getMessage());
        }
    }
    /*=================================RESTAURANT LIST REST CALLS================================================*/

    /*=================================RESTAURANT MENU MODULE REST CALLS================================================*/
    public RestaurantMenuListDto getRestaurantMenuByRestId(Long restaurantId) {
        try {
            RestaurantMenuListDto menuList = restTemplate.getForObject
                    (Constant.RESTAURANT_SERVER_URL + "api/menu/" + restaurantId, RestaurantMenuListDto.class);
            return menuList;
        } catch (Exception e) {
            throw new NoRestaurantFoundException(e.getMessage());
        }
    }
    /*=================================RESTAURANT MENU MODULE REST CALLS================================================*/

}
