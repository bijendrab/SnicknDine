package com.wityo.modules.reservation.service;

import com.wityo.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityo.modules.reservation.dto.ReservationDTO;
import com.wityo.modules.reservation.model.Reservation;

public interface ReservationService {
    CheckReservationResponseDTO checkReservationStatus(Long restaurantId);

    Reservation reserveTable(Long restaurantId, ReservationDTO reservation);
}
