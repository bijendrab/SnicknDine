package com.wityo.modules.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.modules.reservation.dto.ReservationDetailsDTO;
import com.wityo.modules.reservation.service.ReservationService;

@RestController
@RequestMapping(value = "/api/reservation")
@CrossOrigin("*")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/check-reservation/{restaurantId}")
	public ResponseEntity<?> checkReservation(@PathVariable Long restaurantId){
		Map<String, Object> response = new HashMap<String, Object>();
		response.putIfAbsent("message", "Reservation status");
		response.put("body", reservationService.checkReservationStatus(restaurantId));
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.OK);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/reserve-table/{restaurantId}")
	public ResponseEntity<?> reserveTable(@PathVariable Long restaurantId, @RequestBody ReservationDetailsDTO reservation){
		Map<String, Object> response = new HashMap<String, Object>();
		response.putIfAbsent("message", "New reservation successful");
		response.put("body", reservationService.reserveTable(restaurantId, reservation));
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.ACCEPTED);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}