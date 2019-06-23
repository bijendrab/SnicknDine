package com.dao;

import com.dataTransferObjects.CheckRequestDTO;
import com.model.Reservation;
import com.model.RestTable;
import com.model.TimeSpan;

import java.sql.Date;
import java.util.List;

public interface ReservationManager {
    Reservation reserveResult(CheckRequestDTO r);

    List<Reservation> retrieveAllReservation(Date date);

    Reservation processRequest(CheckRequestDTO r);

    RestTable isAvailable
            (Date date,
             List<RestTable> fittingTables,
             TimeSpan reqTimeSpan,int tableNumber);

    Integer isTableAssigned
            (Integer custId);

}
