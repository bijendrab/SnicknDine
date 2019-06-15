package com.service;


import com.dao.TableDao;
import com.model.RestTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(value="TableService")
public class TableServiceImpl implements TableService {
    @Autowired
    private TableDao tableDao;

    public TableDao getTableDao() {
        return tableDao;
    }

    @Transactional
    public RestTable getTableById(int id) {
        return tableDao.getTableById(id);
    }


    public List<RestTable> getTableBySeatSorted(int numberOfSeats) {
        return tableDao.getTableBySeatSorted(numberOfSeats);
    }

    public List<RestTable> getTableByTableNumber(int tableNumber) {
        return tableDao.getTableByTableNumber(tableNumber);
    }


    public List<RestTable>  getAllTables() {
        return tableDao.getAllTables();
    }


    public RestTable createTable(int numberOfSeats,int tableNumber){
        return tableDao.createTable(numberOfSeats,tableNumber);
    }
    public RestTable createTable(RestTable newTable){
        return tableDao.createTable(newTable);
    }

    public RestTable updateTable(int numberOfSeats, int id) throws Exception{
        return tableDao.updateTable(numberOfSeats,id) ;
    }
    public void deleteTableById(int id){
        tableDao.deleteTableById(id);
    }


}
