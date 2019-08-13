package com.wityo.modules.table.model;

import com.wityo.modules.reservation.model.Reservation;

import java.util.List;

public class RestaurantTable {

	private Long id;
	private Long tableNumber;
	private Integer tableSize;
	private Integer qrCode;
	private Long restId;
	
	public RestaurantTable() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Long tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Integer getTableSize() {
		return tableSize;
	}

	public void setTableSize(Integer tableSize) {
		this.tableSize = tableSize;
	}

	public Integer getQrCode() {
		return qrCode;
	}

	public void setQrCode(Integer qrCode) {
		this.qrCode = qrCode;
	}


	public Long getRestId() {
		return restId;
	}

	public void setRestId(Long restId) {
		this.restId = restId;
	}
}
