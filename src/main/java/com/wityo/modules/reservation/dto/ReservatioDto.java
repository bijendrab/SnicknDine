package com.wityo.modules.reservation.dto;

import java.time.LocalDate;

import com.wityo.modules.order.model.TimeSpan;
import com.wityo.modules.table.model.RestaurantTable;

public class ReservatioDto {

	private LocalDate reservationDate;
	private TimeSpan reservationTime;
	private RestaurantTable bookedTable;
	private String otherRequirement;
	
	public LocalDate getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}
	public TimeSpan getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(TimeSpan reservationTime) {
		this.reservationTime = reservationTime;
	}
	public RestaurantTable getBookedTable() {
		return bookedTable;
	}
	public void setBookedTable(RestaurantTable bookedTable) {
		this.bookedTable = bookedTable;
	}
	public String getOtherRequirement() {
		return otherRequirement;
	}
	public void setOtherRequirement(String otherRequirement) {
		this.otherRequirement = otherRequirement;
	}
}
