package com.controller;


import com.dao.ReservationManagerImpl;
import com.dataTransferObjects.CheckRequestDTO;
import com.model.Customer;
import com.model.Reservation;
import com.service.CustomerService;
import com.service.ReservationService;
import com.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TableService tableService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReservationManagerImpl resManager;

    @RequestMapping(value="/reserve",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String reserve(@RequestBody CheckRequestDTO checkRequestDTO, Model model)
            throws
            Exception {

        /* Check if Customer is valid */
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailId = user.getUsername();
        Customer customer = customerService.getCustomerByEmailId(emailId);
        //Optional<Customer> cs = customerService.getCustomerByEmailId(checkRequestDTO.getRelatedCustomer().getCustomerId());

        if (customer==null) {
            throw new Exception("Customer Is Not Valid");
        }
        checkRequestDTO.setRelatedCustomer(customer);


        /* Reserve if possible */
        Reservation res = resManager.reserveResult(checkRequestDTO);

        if (res != null) {
           model.addAttribute("OrderList", res);
           return "jsonTemplate";
        } else {
            throw new Exception();
        }
    }
}
