package com.wityo.modules.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wityo.common.Constant;
import com.wityo.modules.reservation.dto.ReservationDetailsDTO;
import com.wityo.modules.reservation.service.ReservationService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * @Description: Method to check if the user has reserved table or not: API call is going to Restaurant Server  
	 **/
	public int checkReservation(Long restaurantId) {
		return checkReservationStatus(restaurantId);
	}

	/*
	 * @Description: Method to check if the user has reserved table or not: API call is going to Restaurant Server  
	 **/
	public int checkReservationStatus(Long restaurantId) {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			Integer reservationStatus = restTemplate
					.postForObject(Constant.RESTAURANT_SERVER_URL+"api/reservation/"+restaurantId+"/check-reservation",
							customer,
							Integer.class);
			return reservationStatus;
		}catch (Exception e) {
			// exception to be thrown
		}
		return 0;
	}
	
	
	/*
	 * @Description: Method to reserve table for user  
	 *
	 **/
	public ReservationDetailsDTO reserveTable( Long restaurantId, ReservationDetailsDTO reservation) {
		try {
			ReservationDetailsDTO dto = restTemplate
					.postForObject(Constant.RESTAURANT_SERVER_URL+"api/reservation/"+restaurantId+"/reserve",
							reservation,
							ReservationDetailsDTO.class);
			return dto;
		}catch (Exception e) {
			// exception to be thrown
		}
		return null;
	}

}
