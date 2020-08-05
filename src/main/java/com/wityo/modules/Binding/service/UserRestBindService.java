package com.wityo.modules.Binding.service;

import com.wityo.modules.Binding.dto.UserRestBindInput;

public interface UserRestBindService {

    String bindUserToRestaurant(UserRestBindInput userRestBindInput);

    String bindUserToRestaurantCart(Long restaurantId);

    String unBindUserToRestaurantCart(Long restaurantId);

    String bindUserToRestaurantOrder(Long restaurantId);

    String unBindUserToRestaurantOrder(Long restaurantId);


}
