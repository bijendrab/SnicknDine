package com.wityo.modules.reservation.service.impl;

import com.wityo.common.WityoRestAppProperties;
import com.wityo.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityo.modules.reservation.dto.ReservationDTO;
import com.wityo.modules.reservation.dto.ReservationDetails;
import com.wityo.modules.reservation.model.Reservation;
import com.wityo.modules.reservation.service.ReservationService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WityoRestAppProperties wityoRestAppProperties;
    private Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    /*
     * @Description: Method to check if the user has reserved table or not: API call is going to Restaurant Server
     **/
    public CheckReservationResponseDTO checkReservationStatus(Long restaurantId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            CheckReservationResponseDTO response = restTemplate
                    .postForObject(wityoRestAppProperties.getWityoUserAppUrl() + "api/reservation/" + restaurantId + "/check-reservation",
                            customer,
                            CheckReservationResponseDTO.class);
            return response;
        } catch (Exception e) {
            logger.error("Exception in checkReservationStatus inside ReservationServiceImpl:- {}", e);
        }
        return new CheckReservationResponseDTO(Boolean.FALSE, Collections.emptyList());
    }


    /*
     * @Description: Method to reserve table for user
     *
     **/
    public Reservation reserveTable(Long restaurantId, ReservationDTO reservation) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = user.getCustomer();
            ReservationDetails reservationDetails = new ReservationDetails();
            reservationDetails.setReservationDTO(reservation);
            reservationDetails.setCustomerInfo(customer);
            Reservation dto = restTemplate
                    .postForObject(wityoRestAppProperties.getWityoUserAppUrl() + "api/reservation/" + restaurantId + "/reserve",
                        reservationDetails,
                            Reservation.class);
            return dto;
        } catch (Exception e) {
            logger.error("Exception in reserveTabe inside ReservationServiceImpl:- {}", e);
        }
        return null;
    }
}
