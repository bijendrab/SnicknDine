package com.wityo.modules.reservation.service;

import com.wityo.modules.reservation.dto.ReservationDetailsDTO;

public interface ReservationService {
	public int checkReservationStatus(Long restaurantId);
	public ReservationDetailsDTO reserveTable(Long restaurantId, ReservationDetailsDTO reservation);
}
