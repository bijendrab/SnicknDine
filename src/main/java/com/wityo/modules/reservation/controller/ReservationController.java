package com.wityo.modules.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.modules.reservation.dto.ReservatioDto;
import com.wityo.modules.reservation.service.ReservationService;

@RestController
@RequestMapping(value = "/api/reservation")
@CrossOrigin("*")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@PostMapping("/reserve")
	public ResponseEntity<?> reserveUserTable(@RequestBody ReservatioDto reservation){
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("message", "Reservation successful");
			response.put("body", reservationService.reserveTable(reservation));
			response.put("error", false);
			response.put("status", HttpStatus.ACCEPTED);			
		} catch (Exception e) {
			response.put("message", e.getMessage());
			response.put("body", null);
			response.put("error", false);
			response.put("status", HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/check-reservation")
	public ResponseEntity<?> checkIfPreviouslyReserved(){
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("message", "Reservation successful");
			response.put("body", reservationService.checkIfTableReserved());
			response.put("error", false);
			response.put("status", HttpStatus.ACCEPTED);			
		} catch (Exception e) {
			response.put("message", e.getMessage());
			response.put("body", null);
			response.put("error", false);
			response.put("status", HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}