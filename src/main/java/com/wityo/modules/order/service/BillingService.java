package com.wityo.modules.order.service;

import com.wityo.modules.order.dto.BillingDetailResponse;

public interface BillingService {
    BillingDetailResponse getOrderBill(Long restaurantId );
}
