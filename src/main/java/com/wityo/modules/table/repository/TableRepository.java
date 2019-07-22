package com.wityo.modules.table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.table.model.RestaurantTable;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long>{

}
