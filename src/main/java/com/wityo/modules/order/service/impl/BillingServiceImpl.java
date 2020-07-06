package com.wityo.modules.order.service.impl;

import com.wityo.common.WityoRestAppProperties;
import com.wityo.modules.order.dto.BillingDetailResponse;
import com.wityo.modules.order.service.BillingService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BillingServiceImpl implements BillingService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private WityoRestAppProperties wityoRestAppProperties;

    public BillingDetailResponse getOrderBill(Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            BillingDetailResponse response = restTemplate.postForObject(
                wityoRestAppProperties.getWityoUserAppUrl() + "api/billing/forCustomer/" + restaurantId, customer,
                BillingDetailResponse.class);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
