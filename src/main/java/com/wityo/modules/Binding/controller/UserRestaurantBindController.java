package com.wityo.modules.Binding.controller;

import java.util.HashMap;
import java.util.Map;
import com.wityo.common.Constant;
import com.wityo.modules.Binding.dto.UserRestBindInput;
import com.wityo.modules.Binding.service.UserRestBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin("*")
@RestController
@RequestMapping(Constant.BIND_API)
public class UserRestaurantBindController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRestBindService userRestBindService;

    @PostMapping("/user")
    public ResponseEntity<?> linkUserToRest(@RequestBody UserRestBindInput userRestBindInput) {
        Map<String, Object> response = new HashMap<String, Object>();
        String bindStatus= userRestBindService.bindUserToRestaurant(userRestBindInput);
        if(!bindStatus.contains("is active now for the user")){
            response.put("message","Binding of User to Restaurant");
            response.put("body", bindStatus);
            response.put("error", Boolean.TRUE);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.put("message","Binding of User to Restaurant");
            response.put("body",bindStatus);
            response.put("error", Boolean.FALSE);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }
    }
}
