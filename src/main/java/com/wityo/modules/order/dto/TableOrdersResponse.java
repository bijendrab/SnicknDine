package com.wityo.modules.order.dto;

import com.wityo.modules.order.model.CustomerOrder;

import java.util.List;


public class TableOrdersResponse {
	
	List<CustomerOrder> tableOrders;
	public List<CustomerOrder> getTableOrders() {
		return tableOrders;
	}

	public void setTableOrders(List<CustomerOrder> tableOrders) {
		this.tableOrders = tableOrders;
	}
}
