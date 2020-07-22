package com.wityo.modules.reservation.dto;

import com.wityo.modules.user.model.Customer;

public class ReservationDetails {
    private ReservationDTO reservationDTO;
    private Customer customerInfo;

    public ReservationDTO getReservationDTO() {
        return reservationDTO;
    }

    public void setReservationDTO(ReservationDTO reservationDTO) {
        this.reservationDTO = reservationDTO;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }
}
