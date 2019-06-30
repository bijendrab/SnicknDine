package com.dao;

import com.dataTransferObjects.CheckRequestDTO;
import com.model.Reservation;
import com.model.RestTable;
import com.model.TimeSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Repository(value = "ReservationManager")

public class ReservationManagerImpl implements  ReservationManager{
    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private TableDao tableDao;

    public Reservation reserveResult
            (CheckRequestDTO r) {

        Reservation resResult = processRequest(r);

        if (resResult != null) {
            System.out.println("reserved !!!");
            System.out.println(resResult.toString());
            return resResult;
        }

        return null;
    }

    public List<Reservation> retrieveAllReservation
            (Date date) {

        return reservationDao.getAllResForDate(date);
    }

    public Reservation processRequest
            (CheckRequestDTO r) {

        List<RestTable> fittingTables = tableDao.getTableByTableNumber(r.getTableNumber());

        // check if they are available for the given time
        RestTable availableTable = isAvailable(r.getDate(), fittingTables, r.getTs(),r.getTableNumber());

        if (availableTable != null) {        // reserve it if you can
             /*
                    1- reserve the availableTable
                    2- update the table state
                    3- return true
             */
            Date now = Date.valueOf(LocalDate.now());
            return reservationDao.saveReserve(
                    availableTable,
                    r.getRelatedCustomer(),
                    r.getTs(),
                    now,
                    r.getDate(),
                    "");

        }
        return null;
    }


    public RestTable isAvailable
            (Date date,
             List<RestTable> fittingTables,
             TimeSpan reqTimeSpan,int tableNumber) {
         /*
            check for each table whether it is available or not:
                return the first one that is not even booked for the given date.

                check for the timespan intersection if the table was booked already:
                    if it doesn't intersect with requested timespan then :
                        return true.
                    else :
                        return false.
         */

        for (RestTable table : fittingTables) {
            /* fetch all the reservation for this table */
            List<Reservation> allResForThisTable = reservationDao.getByTableId(table.getId(), date);

            if (allResForThisTable == null || allResForThisTable.size() == 0) {    // if not reserved before
                return table;
            } else {    // if reserved already check for TS intersection
                for (Reservation resv : allResForThisTable) {
                    if (resv.getRelatedTable().getTableNumber()==tableNumber) {
                        return table;
                    }
                }
            }
        }

        //TODO: throw an exceptions
        return null;
    }
    public Integer isTableAssigned
            (Integer custId) {

        Reservation allResForThisTable = reservationDao.getByCustomerId(custId);

        if (allResForThisTable == null ) {    // if not reserved before
            return 0;
        }
        else {    // if reserved already check for TS intersection
            if (allResForThisTable.getRelatedCustomer().getCustomerId() == custId) {
                    return allResForThisTable.getRelatedTable().getTableNumber();
                }
            }

        return 0;
    }

}

