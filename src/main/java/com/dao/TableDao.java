package com.dao;

import com.model.RestTable;

import java.util.List;

public interface TableDao {
    RestTable getTableById(int id);

    List<RestTable> getTableBySeatSorted(int numberOfSeats);

    List<RestTable> getTableByTableNumber(int tableNumber);

    List<RestTable> getAllTables();

    RestTable createTable(int numberOfSeats,int tableNumber);

    RestTable createTable(RestTable newTable);

    RestTable updateTable(int numberOfSeats, int id) throws Exception;

    void deleteTableById(int id);
}
