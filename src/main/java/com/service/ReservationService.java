package com.service;

import com.model.Customer;
import com.model.Reservation;
import com.model.RestTable;
import com.model.TimeSpan;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface ReservationService {
    Reservation saveReserve(RestTable table,
                            Customer customer,
                            TimeSpan ts,
                            Date submissionDate,
                            Date reservationDate,
                            String otherReq);

    Reservation getByResId(Integer resId, Date date);

    Reservation getByResId(Integer resId);

    ArrayList<Reservation> getByTableId(Integer tableId, Date date, TimeSpan ts);

    List<Reservation> getByTableId(Integer tableId, Date date);

    List<Reservation> getAllResBetweenDates(Date startDate, Date endDate) throws Exception;

    List<Reservation> getAllResForDate(Date date);
}
