package com.wityo.modules.reservation.service;

import com.wityo.modules.reservation.dto.ReservatioDto;
import com.wityo.modules.reservation.exception.ReservationVerificationException;
import com.wityo.modules.reservation.exception.UnableToReserveTableException;
import com.wityo.modules.reservation.model.Reservation;

public interface ReservationService {
	public Reservation reserveTable(ReservatioDto reservationDto) throws UnableToReserveTableException;
	public Long checkIfTableReserved() throws ReservationVerificationException;
}
