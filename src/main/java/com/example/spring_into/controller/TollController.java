package com.example.spring_into.controller;

import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.dto.ValidityResponse;
import com.example.spring_into.model.TollPass;
import com.example.spring_into.service.OwnerService;
import com.example.spring_into.service.TollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/toll")
public class TollController {

    @Autowired
    TollService tollService;
    @Autowired
    OwnerService ownerService;

    @PostMapping
    public ResponseEntity<TollResponse> addToll(@Valid @RequestBody TollRequest tollRequest) {
        return new ResponseEntity<>(tollService.addToll(tollRequest),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ValidityResponse> checkValidity(@RequestParam(name = "regNumber") String regNumber,
                                                          @RequestParam(name = "country") String country) {
        return new ResponseEntity<>(tollService.checkValidity(regNumber, country), HttpStatus.FOUND);
    }

    @GetMapping(path = {"owner/{ownerId}"})
    public Set<TollPass> getTollForUser(@PathVariable("ownerId") Long ownerId) {
        return ownerService.getTollsForOwner(ownerId);
    }

    @DeleteMapping(path = {"/{id}"})
    public void deleteToll(@PathVariable("id") Long id) {
        tollService.deleteToll(id);
    }


}
