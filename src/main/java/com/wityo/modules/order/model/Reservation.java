package com.wityo.modules.order.model;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.wityo.modules.user.model.Customer;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reservationId;
	private LocalDate submissionDate;
	private LocalDate reservationDate;
	
	@Embedded
	@NotNull
	private TimeSpan reservationTime;
	
	private String otherRequirement;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer relatedCustomer;
	
	@ManyToOne
	@JoinColumn(name = "related_table_id")
	private RelatedTable relatedTable;
	
	public Reservation() {}

	public Reservation(Long reservationId, LocalDate submissionDate, LocalDate reservationDate,
			@NotNull TimeSpan reservationTime, String otherRequirement, Customer relatedCustomer,
			RelatedTable relatedTable) {
		this.reservationId = reservationId;
		this.submissionDate = submissionDate;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.otherRequirement = otherRequirement;
		this.relatedCustomer = relatedCustomer;
		this.relatedTable = relatedTable;
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

	public Customer getRelatedCustomer() {
		return relatedCustomer;
	}

	public void setRelatedCustomer(Customer relatedCustomer) {
		this.relatedCustomer = relatedCustomer;
	}

	public RelatedTable getRelatedTable() {
		return relatedTable;
	}

	public void setRelatedTable(RelatedTable relatedTable) {
		this.relatedTable = relatedTable;
	}

}
