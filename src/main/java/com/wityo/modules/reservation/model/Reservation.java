package com.wityo.modules.reservation.model;

import java.time.LocalDate;

import com.wityo.modules.order.model.TimeSpan;
import com.wityo.modules.table.model.RestaurantTable;

public class Reservation {
	private Long id;
	private LocalDate submissionDate;
	private LocalDate reservationDate;
	private TimeSpan reservationTime;
	private String otherRequirements;
	private String customerInfo;
	private RestaurantTable relatedTable;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(LocalDate submissionDate) {
		this.submissionDate = submissionDate;
	}
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
	public String getOtherRequirements() {
		return otherRequirements;
	}
	public void setOtherRequirements(String otherRequirements) {
		this.otherRequirements = otherRequirements;
	}
	public String getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}
	public RestaurantTable getRelatedTable() {
		return relatedTable;
	}
	public void setRelatedTable(RestaurantTable relatedTable) {
		this.relatedTable = relatedTable;
	}

}
