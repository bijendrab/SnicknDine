package com.wityo.modules.order.dto;

import java.util.List;

import javax.persistence.criteria.Order;

public class TableOrdersResponse {
	
	List<Order> tableOrders;
	public List<Order> getTableOrders() {
		return tableOrders;
	}

	public void setTableOrders(List<Order> tableOrders) {
		this.tableOrders = tableOrders;
	}
}
