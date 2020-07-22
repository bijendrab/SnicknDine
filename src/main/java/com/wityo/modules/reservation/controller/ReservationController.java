package com.wityo.modules.reservation.controller;

import com.wityo.modules.reservation.dto.CheckReservationResponseDTO;
import com.wityo.modules.reservation.dto.ReservationDTO;
import com.wityo.modules.reservation.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/reservation")
@CrossOrigin("*")
public class ReservationController {

    @Autowired
    ReservationService reservationService;
    @ApiOperation(value = "get reservation status ", response = CheckReservationResponseDTO.class)
    @GetMapping("/check-reservation/{restaurantId}")
    public ResponseEntity<?> checkReservation(@PathVariable Long restaurantId) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "Reservation status");
        response.put("body", reservationService.checkReservationStatus(restaurantId));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/reserve-table/{restaurantId}")
    public ResponseEntity<?> reserveTable(@PathVariable Long restaurantId, @RequestBody ReservationDTO reservation) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.putIfAbsent("message", "New reservation successful");
        response.put("body", reservationService.reserveTable(restaurantId, reservation));
        response.put("error", Boolean.FALSE);
        response.put("status", HttpStatus.ACCEPTED);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
