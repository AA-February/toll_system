package com.example.spring_into.controller;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.OwnerResponse;
import com.example.spring_into.model.Owner;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/owner")
public class OwnerController {

    @Autowired
    OwnerService ownerService;
    @Autowired
    OwnerRepository ownerRepository;

    @PostMapping(path = "/{ownerId}")
    Owner updateOwner(@RequestBody OwnerRequest request,
                      @PathVariable("ownerId") Long ownerId) {
        return ownerService.updateOwner(request, ownerId);
    }

    @DeleteMapping(path = {"/{ownerId}"})
    void deleteOwner(@PathVariable("ownerId") Long ownerId) {
        ownerService.deleteOwnerById(ownerId);
    }

    @PostMapping
    ResponseEntity<OwnerResponse> addOwner(@RequestBody OwnerRequest ownerRequest) {
        ownerService.addOwner(ownerRequest);

        return new ResponseEntity<>(ownerService.addOwner(ownerRequest),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ownerService.findOwnerById(id),
                HttpStatus.FOUND);
    }
}
