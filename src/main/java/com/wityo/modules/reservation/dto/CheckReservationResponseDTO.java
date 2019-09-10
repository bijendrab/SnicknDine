package com.wityo.modules.reservation.dto;

import com.wityo.modules.table.model.RestaurantTable;

import java.util.List;

public class CheckReservationResponseDTO {

    private Boolean reservationStatus;
    private List<RestaurantTable> restaurantTable;

    public CheckReservationResponseDTO() {
    }

    public CheckReservationResponseDTO(Boolean reservationStatus, List<RestaurantTable> restaurantTable) {
        this.reservationStatus = reservationStatus;
        this.restaurantTable = restaurantTable;
    }

    public Boolean getReservationStatus() {
        return reservationStatus;
    }

    public void ListReservationStatus(Boolean reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public List<RestaurantTable> getRestaurantTable() {
        return restaurantTable;
    }

    public void ListRestaurantTable(List<RestaurantTable> restaurantTable) {
        this.restaurantTable = restaurantTable;
    }


}
