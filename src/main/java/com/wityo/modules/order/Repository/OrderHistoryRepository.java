package com.wityo.modules.order.Repository;

import java.util.List;
import com.wityo.modules.order.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findAllByCustomerIdAndRestaurantId(Long customerId, Long restaurantId);
}
