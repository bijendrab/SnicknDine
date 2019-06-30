package com.controller;

import com.model.RestTable;
import com.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.service.TableService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class TableController {

    @Autowired
    private TableService tableservice;

    @RequestMapping(value="/table",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createNewTable(@RequestBody RestTable resttable, BindingResult result,Model model) {

        //Cart cart =cartService.validate(cartId);
        if (result.hasErrors())
            return null;
        RestTable createdTable = tableservice.createTable(resttable);
        model.addAttribute("OrderList", createdTable);
        return "jsonTemplate";
    }
}
