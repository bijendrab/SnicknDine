package com.wityo.modules.reservation.dto;

import com.wityo.modules.table.model.RestaurantTable;

import java.util.Set;

public class ReservationResponseDTO {

    private Integer reservationStatus;
    private Set<RestaurantTable> restaurantTable;

    public Integer getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(Integer reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Set<RestaurantTable> getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(Set<RestaurantTable> restaurantTable) {
        this.restaurantTable = restaurantTable;
    }
}
