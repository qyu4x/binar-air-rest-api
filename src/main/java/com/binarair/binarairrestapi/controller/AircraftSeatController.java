package com.binarair.binarairrestapi.controller;

import com.binarair.binarairrestapi.model.request.AircraftSeatRequest;
import com.binarair.binarairrestapi.model.response.AircraftSeatResponse;
import com.binarair.binarairrestapi.model.response.WebResponse;
import com.binarair.binarairrestapi.service.AircraftSeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/aircraftseat")
public class AircraftSeatController {

    private final static Logger log = LoggerFactory.getLogger(AircraftSeatController.class);

    private final AircraftSeatService aircraftSeatService;

    @Autowired
    public AircraftSeatController(AircraftSeatService aircraftSeatService) {
        this.aircraftSeatService = aircraftSeatService;
    }

    @ResponseBody
    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUYER')")
    public ResponseEntity<WebResponse<List<AircraftSeatResponse>>> findAvailableSeat(@RequestParam("aircraftid") String aircraftid) {
        log.info("Calling controller find available seat - airport");
        List<AircraftSeatResponse> aircraftSeatResponses = aircraftSeatService.findAllAvailableSeat(aircraftid);
        WebResponse webResponse = new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                aircraftSeatResponses
        );
        log.info("Successful get available aircraft seat data");
        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/reserved")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUYER')")
    public ResponseEntity<WebResponse<List<AircraftSeatResponse>>> findReservedSeat(@RequestParam("aircraftid") String aircraftid) {
        log.info("Calling controller find reserved seat - airport");
        List<AircraftSeatResponse> aircraftSeatResponses = aircraftSeatService.findAllReservedSeat(aircraftid);
        WebResponse webResponse = new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                aircraftSeatResponses
        );
        log.info("Successful get reserved aircraft seat data");
        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUYER')")
    public ResponseEntity<WebResponse<List<AircraftSeatResponse>>> getAllAircraftSeat(@RequestParam("aircraftid") String aircraftid) {
        log.info("Calling controller get all aircraft seat - airport");
        List<AircraftSeatResponse> aircraftSeatResponses = aircraftSeatService.getAllByAircraftId(aircraftid);
        WebResponse webResponse = new WebResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                aircraftSeatResponses
        );
        log.info("Successful get all aircraft seat data");
        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<WebResponse<AircraftSeatResponse>> save(@Valid @RequestBody AircraftSeatRequest aircraftSeatRequest) {
        log.info("call controller save - aircraft seat");
        AircraftSeatResponse aircraftSeatResponse = aircraftSeatService.save(aircraftSeatRequest);
        log.info("successful save aircraft seat data");
        WebResponse webResponse = new WebResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                aircraftSeatResponse
        );
        return new ResponseEntity<>(webResponse, HttpStatus.CREATED);
    }

}
