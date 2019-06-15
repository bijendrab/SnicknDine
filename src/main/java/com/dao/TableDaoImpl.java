package com.dao;

import com.model.RestTable;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository(value = "TableDao")
public class TableDaoImpl implements TableDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public RestTable getTableById
            (final int id) {

        Session session = sessionFactory.openSession();

        RestTable tbl = (RestTable) session.get(RestTable.class, id);

        session.close();
        return tbl;
    }

    @Override
    public List<RestTable> getTableBySeatSorted
            (int minNumberOFSeats) {

        Session session = sessionFactory.openSession();

        String hql = "FROM RestTable  where numberOfSeats >= :minSeats order by numberOfSeats";
        Query query = session.createQuery(hql).setParameter("minSeats", minNumberOFSeats);

        @SuppressWarnings("unchecked")
        List<RestTable> tables = query.list();

        session.close();
        return tables;
    }
    @Override
    public List<RestTable> getTableByTableNumber
            (int tableNumber) {

        Session session = sessionFactory.openSession();

        String hql = "FROM RestTable  where tableNumber = :tableNum order by numberOfSeats";
        Query query = session.createQuery(hql).setParameter("tableNum", tableNumber);

        @SuppressWarnings("unchecked")
        List<RestTable> tables = query.list();

        session.close();
        return tables;
    }

    @Override
    public List<RestTable> getAllTables() {

        Session session = sessionFactory.openSession();

        String hql = "FROM restaurantTable";
        Query query = session.createQuery(hql);

        @SuppressWarnings("unchecked")
        List<RestTable> tables = query.list();

        session.close();
        return tables;
    }

    @Override
    public RestTable createTable
            (int numberOfSeats,int tableNumber) {

        Session session = sessionFactory.openSession();
        RestTable newTable = new RestTable();
        newTable.setNumberOfSeats(numberOfSeats);
        newTable.setTableNumber(tableNumber);

        session.beginTransaction();
        session.save(newTable);
        session.getTransaction().commit();
        System.out.println("Table Created Successfully...");

        session.close();
        return newTable;
    }

    @Override
    public RestTable createTable
            (RestTable newTable) {

        return this.createTable(newTable.getNumberOfSeats(),newTable.getTableNumber());
    }

    @Override
    public void deleteTableById
            (int id) {

        Session session =sessionFactory.openSession();

        RestTable tbl = getTableById(id);
        session.delete(tbl);
        session.getTransaction().commit();

        System.out.println("Successfully deleted " + tbl.toString());
    }

    @Override
    public RestTable updateTable
            (int numberOfSeats, int id)
            throws
            Exception {

        if (getTableById(id)!=null) {
            throw new Exception("No Table Found To Update!");
        }

        /* Changing the attributes of the existingTable with newTable */
        Session session = sessionFactory.openSession();


        session.beginTransaction();
        RestTable tbl = (RestTable) session.get(RestTable.class,id);
        tbl.setNumberOfSeats(numberOfSeats);
        session.merge(tbl);
        session.getTransaction().commit();
        session.close();

        return getTableById(id);
    }
}
