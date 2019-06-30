package com.service;

import com.dao.ReservationDao;
import com.model.Customer;
import com.model.Reservation;
import com.model.RestTable;
import com.model.TimeSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service(value="ReservationService")
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Transactional
    public Reservation saveReserve(RestTable table,
                                   Customer customer,
                                   TimeSpan ts,
                                   Date submissionDate,
                                   Date reservationDate,
                                   String otherReq) {
        return reservationDao.saveReserve(table,customer,ts,submissionDate,reservationDate,otherReq);
    }


    public Reservation getByResId(Integer resId, Date date) {
        return reservationDao.getByResId(resId,date);
    }


    public Reservation getByResId(Integer resId) {
        return reservationDao.getByResId(resId);
    }


    public ArrayList<Reservation> getByTableId(Integer tableId, Date date, TimeSpan ts){
        return reservationDao.getByTableId(tableId,date,ts);
    }
    public List<Reservation> getByTableId(Integer tableId, Date date){
        return reservationDao.getByTableId(tableId,date);
    }

    public Reservation getByCustomerId(Integer custId){
        return reservationDao.getByCustomerId(custId);
    }
    public List<Reservation> getAllResBetweenDates(Date startDate, Date endDate) throws Exception{
        return reservationDao.getAllResBetweenDates(startDate,endDate);
    }
    public List<Reservation> getAllResForDate(Date date){
        return reservationDao.getAllResForDate(date);
    }
}
