package com.wityo.modules.reservation.dto;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

import com.wityo.modules.order.model.TimeSpan;
import com.wityo.modules.user.model.Customer;

public class ReservationDetailsDTO {
	
	@NotNull
    private LocalDate date;

    @NotNull
    private Integer numberOfSeats;

    @NotNull
    private Integer tableNumber;

    private Customer customerInfo;

    @NotNull
    @Embedded
    private TimeSpan timeSpan;

    public ReservationDetailsDTO() {}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Customer getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(Customer customerInfo) {
		this.customerInfo = customerInfo;
	}

	public TimeSpan getTs() {
		return getTimeSpan();
	}

	public TimeSpan getTimeSpan() {
		return timeSpan;
	}

	public void setTs(TimeSpan ts) {
		setTimeSpan(ts);
	}

	public void setTimeSpan(TimeSpan ts) {
		this.timeSpan = ts;
	}

}
