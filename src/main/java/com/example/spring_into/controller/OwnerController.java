package com.example.spring_into.controller;
import com.example.spring_into.dto.LoginRequest;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.OwnerResponse;
import com.example.spring_into.model.Owner;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/owner")
@Tag(name = "Owner API",description = "API for managing owners")
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @Operation(summary = "Update owner by ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Owner successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Owner.class)))
    })
    @PutMapping(path = "/{ownerId}")
    Owner updateOwner(@Valid @RequestBody OwnerRequest request,
                      @PathVariable("ownerId") Long ownerId) {
        return ownerService.updateOwner(request, ownerId);
    }

    @DeleteMapping(path = {"/{ownerId}"})
    void deleteOwner(@PathVariable("ownerId") Long ownerId) {
        ownerService.deleteOwnerById(ownerId);
    }

    @PostMapping
    ResponseEntity<OwnerResponse> addOwner(@Valid @RequestBody OwnerRequest ownerRequest) {
        return new ResponseEntity<>(ownerService.addOwner(ownerRequest),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ownerService.findOwnerById(id),
                HttpStatus.FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<OwnerResponse> login(@Valid @RequestBody LoginRequest request) {
      return new ResponseEntity<>(ownerService.login(request),HttpStatus.OK);
    }

}
