package com.wityo.modules.reservation.service.impl;

import java.util.Collections;

import com.wityo.modules.reservation.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wityo.common.Constant;
import com.wityo.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityo.modules.reservation.dto.ReservationDetailsDTO;
import com.wityo.modules.reservation.service.ReservationService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private RestTemplate restTemplate;
	private Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
	/*
	 * @Description: Method to check if the user has reserved table or not: API call is going to Restaurant Server  
	 **/
	public CheckReservationResponseDTO checkReservationStatus(Long restaurantId) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			CheckReservationResponseDTO response = restTemplate
					.postForObject(Constant.RESTAURANT_SERVER_URL+"api/reservation/"+restaurantId+"/check-reservation",
							customer,
							CheckReservationResponseDTO.class);
			return response;
		}catch (Exception e) {
			logger.error("Exception in checkReservationStatus inside ReservationServiceImpl:- {}", e);
		}
		return new CheckReservationResponseDTO(Boolean.FALSE, Collections.emptyList());
	}
	
	
	/*
	 * @Description: Method to reserve table for user  
	 *
	 **/
	public Reservation reserveTable( Long restaurantId, ReservationDetailsDTO reservation) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			reservation.setCustomerInfo(customer);
			Reservation dto = restTemplate
					.postForObject(Constant.RESTAURANT_SERVER_URL+"api/reservation/"+restaurantId+"/reserve",
							reservation,
							Reservation.class);
			return dto;
		}catch (Exception e) {
			logger.error("Exception in reserveTabe inside ReservationServiceImpl:- {}", e);
		}
		return null;
	}
}
