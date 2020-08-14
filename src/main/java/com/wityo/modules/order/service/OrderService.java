package com.wityo.modules.order.service;

import java.util.List;
import com.wityo.modules.order.dto.EndDiningInfo;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.dto.TableOrdersResponse;
import com.wityo.modules.order.dto.UpdateOrderItemDTO;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.model.OrderHistory;

public interface OrderService {

    CustomerOrder placeCustomerOrder(Long restaurantId);

    TableOrdersResponse getCustomerTableOrders(Long restaurantId);

    CustomerOrder updateCustomerOrderedItem(UpdateOrderItemDTO dto, Long restaurantId);

    Boolean deleteCustomerOrderedItem(UpdateOrderItemDTO dto, Long restaurantId);

    Boolean endDining(EndDiningInfo endDiningInfo);

    List<OrderHistory> getPastOrders();
}
