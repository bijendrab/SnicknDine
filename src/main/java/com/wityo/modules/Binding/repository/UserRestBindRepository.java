package com.wityo.modules.Binding.repository;

import java.util.List;
import com.wityo.modules.Binding.model.UserRestaurantBind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRestBindRepository extends JpaRepository<UserRestaurantBind, Long> {
    List<UserRestaurantBind> findAllByUserId(Long userId);
    UserRestaurantBind findAllByUserIdAndRestaurantId(Long userId, Long restaurantId);
    List<UserRestaurantBind> findAllByUserIdAndRestaurantIdNot(Long userId, Long restaurantId);
    UserRestaurantBind findAllByUserIdAndCartStatus(Long userId, Boolean cartStatus);
}
