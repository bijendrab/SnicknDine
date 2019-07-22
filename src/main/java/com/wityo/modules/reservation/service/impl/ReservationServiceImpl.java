package com.wityo.modules.reservation.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wityo.modules.reservation.dto.ReservatioDto;
import com.wityo.modules.reservation.exception.ReservationVerificationException;
import com.wityo.modules.reservation.exception.UnableToReserveTableException;
import com.wityo.modules.reservation.model.Reservation;
import com.wityo.modules.reservation.repository.ReservationRepository;
import com.wityo.modules.reservation.service.ReservationService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	ReservationRepository reservationRepository;
	
	public Reservation reserveTable(ReservatioDto reservationDto) throws UnableToReserveTableException{
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			Reservation reservation = new Reservation();
			reservation.setCustomer(customer);
			reservation.setOtherRequirement(reservationDto.getOtherRequirement());
			reservation.setReservationDate(LocalDate.now());
			reservation.setReservationTime(reservationDto.getReservationTime());
			reservation.setRestaurantTable(reservation.getRestaurantTable());
			reservation.setSubmissionDate(LocalDate.now());
			return reservationRepository.save(reservation);
		}catch (Exception e) {
			throw new UnableToReserveTableException("Unable to book table at the moment, please try again!");
		}
	}
	
	public Long checkIfTableReserved() throws ReservationVerificationException{
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = user.getCustomer();
			Reservation reservation = reservationRepository.findByCustomerId(customer.getCustomerId());
			if(reservation.getRestaurantTable() == null) {
				return 0L; 
			} else {
				return reservation.getRestaurantTable().getRestaurantTableId();
			}
		} catch (Exception e) {
			 throw new ReservationVerificationException("Unable to verify reservation at the moment, please try again!");
		}
	}

}
