package com.wityo.modules.Binding.repository;

import java.util.List;
import com.wityo.modules.Binding.model.UserRestaurantBind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRestBindRepository extends JpaRepository<UserRestaurantBind, Long> {
    List<UserRestaurantBind> findAllByUserId(String userId);
    UserRestaurantBind findAllByUserIdAndRestaurantId(String userId, Long restaurantId);
    List<UserRestaurantBind> findAllByUserIdAndRestaurantIdNot(String userId, Long restaurantId);
    UserRestaurantBind findAllByUserIdAndCartStatus(String userId, Boolean cartStatus);
    UserRestaurantBind findAllByUserIdAndOrderStatus(String userId, Boolean orderStatus);
}
