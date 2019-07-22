package com.wityo.modules.reservation.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.wityo.modules.order.model.RestaurantTable;
import com.wityo.modules.order.model.TimeSpan;
import com.wityo.modules.user.model.Customer;

@Entity
public class Reservation {

	@Id
	@Column(name = "reservation_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reservationId;
	private LocalDate submissionDate;
	private LocalDate reservationDate;
	
	@Embedded
	@NotNull
	private TimeSpan reservationTime;
	
	private String otherRequirement;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.ALL)	
	@JoinColumn(name = "restaurant_table_id")
	private RestaurantTable restaurantTable;
	
	public Reservation() {}

	public Reservation(Long reservationId, LocalDate submissionDate, LocalDate reservationDate,
			@NotNull TimeSpan reservationTime, String otherRequirement, Customer relatedCustomer,
			RestaurantTable relatedTable) {
		this.reservationId = reservationId;
		this.submissionDate = submissionDate;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.otherRequirement = otherRequirement;
		this.customer = relatedCustomer;
		this.restaurantTable = relatedTable;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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

	public String getOtherRequirement() {
		return otherRequirement;
	}

	public void setOtherRequirement(String otherRequirement) {
		this.otherRequirement = otherRequirement;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer relatedCustomer) {
		this.customer = relatedCustomer;
	}

	public RestaurantTable getRestaurantTable() {
		return restaurantTable;
	}

	public void setRestaurantTable(RestaurantTable relatedTable) {
		this.restaurantTable = relatedTable;
	}

}
