package com.wityo.modules.table.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityo.modules.table.repository.TableRepository;
import com.wityo.modules.table.service.TableService;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	TableRepository tableRepository;

}
