package com.wityo.modules.reservation.service;

import com.wityo.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityo.modules.reservation.dto.ReservationDetailsDTO;

public interface ReservationService {
	public CheckReservationResponseDTO checkReservationStatus(Long restaurantId);
	public ReservationDetailsDTO reserveTable(Long restaurantId, ReservationDetailsDTO reservation);
}
