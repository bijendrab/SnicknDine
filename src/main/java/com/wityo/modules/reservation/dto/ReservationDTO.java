package com.wityo.modules.reservation.dto;

import com.wityo.modules.order.model.TimeSpan;
import com.wityo.modules.user.model.Customer;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationDTO {

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer numberOfSeats;

    @NotNull
    private Integer tableNumber;

    @NotNull
    @Embedded
    private TimeSpan reservationTime;

    public ReservationDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public TimeSpan getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(TimeSpan reservationTime) {
        this.reservationTime = reservationTime;
    }
}
