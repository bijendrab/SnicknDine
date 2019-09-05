package com.wityo.modules.reservation.service;

import com.wityo.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityo.modules.reservation.dto.ReservationDetailsDTO;
import com.wityo.modules.reservation.model.Reservation;

public interface ReservationService {
	public CheckReservationResponseDTO checkReservationStatus(Long restaurantId);
	public Reservation reserveTable(Long restaurantId, ReservationDetailsDTO reservation);
}
