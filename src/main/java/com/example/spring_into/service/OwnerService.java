package com.example.spring_into.service;

import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.OwnerResponse;
import com.example.spring_into.model.Owner;
import com.example.spring_into.model.TollPass;

import java.util.Set;

public interface OwnerService {

    Set<TollPass> getTollsForOwner(Long ownerId);

    void deleteOwnerById(Long id);

    Owner updateOwner(OwnerRequest request, Long ownerId);

    OwnerResponse addOwner(OwnerRequest ownerRequest);

    OwnerResponse findOwnerById(Long id);
}
