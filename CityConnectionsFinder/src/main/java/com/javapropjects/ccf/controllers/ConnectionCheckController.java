package com.javapropjects.ccf.controllers;

import com.javapropjects.ccf.exceptions.InvalidRequestException;
import com.javapropjects.ccf.exceptions.RouteNotFoundException;
import com.javapropjects.ccf.model.PathStatus;
import com.javapropjects.ccf.model.RouteResponse;
import com.javapropjects.ccf.services.ConnectionCheckService;
import com.javapropjects.ccf.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionCheckController {
    
    @Autowired
    ConnectionCheckService connectionCheckService;

    @Autowired
    CommonUtil util;

    @GetMapping("/connected")
    public RouteResponse checkCityConnection(@RequestParam String origin, @RequestParam String destination)
            throws Exception {

        if(util.isEmpty(origin) || util.isEmpty(destination)) 
            throw new InvalidRequestException("Source / Destination cannot be empty");
            
        String status = connectionCheckService.checkIfCitiesAreConnected(origin, destination);
        if(status == PathStatus.INVALID_PATH.toString()) {
            throw new RouteNotFoundException("Invalid Source/Origin");
        }
        
        RouteResponse response = new RouteResponse(HttpStatus.OK.toString(), status);
        return response;
    }
}