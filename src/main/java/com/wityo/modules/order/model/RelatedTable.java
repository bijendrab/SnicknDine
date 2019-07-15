package com.wityo.modules.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class RelatedTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer relatedTableId;
	@Min(2)
	private Integer numberOfSeats;
	private Integer tableNumber;
	
	public RelatedTable() {}

	public Integer getRelatedTableId() {
		return relatedTableId;
	}

	public void setRelatedTableId(Integer relatedTableId) {
		this.relatedTableId = relatedTableId;
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
