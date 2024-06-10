package com.example.spring_into.controller;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.model.Owner;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<String> addOwner(@RequestBody OwnerRequest ownerRequest){
        ownerService.addOwner(ownerRequest);
        return null;
    }

    @GetMapping("/{id}")
    public Owner getById(@PathVariable("id") long id){
        return ownerRepository.findById(id).get();
    }
}
