package com.wityo.modules.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class RestaurantTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer restaurantTableId;
	@Min(2)
	private Integer numberOfSeats;
	private Integer tableNumber;
	
	public RestaurantTable() {}

	public Integer getRestaurantTableId() {
		return restaurantTableId;
	}

	public void setRestaurantTableId(Integer relatedTableId) {
		this.restaurantTableId = relatedTableId;
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
	
}
