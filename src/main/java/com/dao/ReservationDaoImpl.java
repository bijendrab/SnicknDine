package com.dao;

import com.model.Customer;
import com.model.Reservation;
import com.model.RestTable;
import com.model.TimeSpan;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "ReservationDao")
public class ReservationDaoImpl implements ReservationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Reservation saveReserve
            (RestTable table,
             Customer customer,
             TimeSpan ts,
             Date submissionDate,
             Date reservationDate,
             String otherReq) {
        Session session = sessionFactory.openSession();

        Reservation res = new Reservation();
        res.setRelatedTable(table);
        res.setRelatedCustomer(customer);
        res.setReservationTime(ts);
        res.setSubmissionDate(submissionDate);
        res.setReservationDate(reservationDate);
        res.setOtherRequirements(otherReq);

        session.beginTransaction();
        session.save(res);
        session.getTransaction().commit();
        session.close();

        return res;
    }
    @Override
    public Reservation getByResId
            (Integer resId) {

        Session session = sessionFactory.openSession();

        Reservation reservation = (Reservation) session.get(Reservation.class, resId);

        session.close();

        if (reservation != null) {
            return reservation;
        }

        return null;
    }
    @Override
    public Reservation getByResId
            (Integer resId, Date date) {

        Session session = sessionFactory.openSession();

        String hql = "FROM Reservation AS Res " +
                "WHERE Res.id = :resId AND " +
                "reservationDate = :resDate";

        Query query = session.
                createQuery(hql).
                setParameter("resId", resId).
                setParameter("resDate", date);

        @SuppressWarnings("unchecked")
        List<Reservation> reservations = query.list();
        session.close();

        if (reservations != null && reservations.size() > 0) {
            return reservations.get(0);
        }

        return null;
    }
    @Override
    public List<Reservation> getByTableId
            (Integer tableId, Date date) {
        Session session = sessionFactory.openSession();

        String hql = "FROM Reservation AS Res " +
                "WHERE Res.relatedTable.id = :tblId AND " +
                "Res.reservationDate = :resDate";

        Query query = session.
                createQuery(hql).
                setParameter("tblId", tableId).
                setParameter("resDate", date);

        String q = query.getQueryString();

        @SuppressWarnings("unchecked")
        List<Reservation> reservation = query.list();
        session.close();

        if (reservation != null && reservation.size() > 0) {
            return reservation;
        }

        return null;
    }
    @Override
    public Reservation getByCustomerId
            (Integer custId) {
        Session session = sessionFactory.openSession();

        String hql = "FROM Reservation AS Res " +
                "WHERE Res.relatedCustomer.id = :custId";

        Query query = session.
                createQuery(hql).
                setParameter("custId", custId);

        String q = query.getQueryString();

        @SuppressWarnings("unchecked")
        List<Reservation> reservation = query.list();
        session.close();

        if (reservation != null && reservation.size() > 0) {
            return reservation.get(0);
        }

        return null;
    }

    @Override
    public List<Reservation> getAllResForDate
            (Date date) {

        Session session = sessionFactory.openSession();

        String hql = "FROM Reservation " +
                "WHERE reservationDate = :resDate";

        Query query = session.
                createQuery(hql).
                setParameter("resDate", date);

        @SuppressWarnings("unchecked")
        List<Reservation> reservation = query.list();
        session.close();

        if (reservation != null && reservation.size() > 0) {
            return reservation;
        }

        return null;
    }

    @Override
    public List<Reservation> getAllResBetweenDates
            (Date startDate, Date endDate)
            throws
            Exception {
        Session session = sessionFactory.openSession();

        String hql = "FROM Reservation " +
                "WHERE reservationDate >= :startDate AND " +
                "reservationDate <= :endDate";

        Query query = session.
                createQuery(hql).
                setParameter("startDate", startDate).
                setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Reservation> reservations = query.list();
        session.close();

        return reservations;
    }
    @Override
    public ArrayList<Reservation> getByTableId
            (Integer tableId, Date date, TimeSpan ts) {
        //TODO
        return null;
    }
}
