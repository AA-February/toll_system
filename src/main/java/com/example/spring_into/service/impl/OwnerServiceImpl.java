package com.example.spring_into.service.impl;

import com.example.spring_into.converter.OwnerConverter;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.OwnerResponse;
import com.example.spring_into.exception.RecordNotFoundException;
import com.example.spring_into.model.Owner;
import com.example.spring_into.model.TollPass;
import com.example.spring_into.repository.OwnerRepository;
import com.example.spring_into.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final OwnerConverter ownerConverter;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository,
                            OwnerConverter ownerConverter) {
        this.ownerRepository = ownerRepository;
        this.ownerConverter = ownerConverter;
    }

    @Override
    public Set<TollPass> getTollsForOwner(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            return new HashSet<>();
        } else {
            return owner.get().getTollPass();
        }
    }

    @Override
    public void deleteOwnerById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Owner updateOwner(OwnerRequest request, Long ownerId) {
        Owner existingOwner = ownerRepository.findById(ownerId).orElseThrow(()->new RecordNotFoundException(String.format("Customer with id: %s not exists", ownerId)));

        if (request.getLastName() != null) {
            existingOwner.setLastName(request.getLastName());
        }
        if (request.getFirstName() != null) {
            existingOwner.setFirstName(request.getFirstName());
        }
        if (request.getAddress() != null) {
            existingOwner.setAddress(request.getAddress());
        }
        existingOwner.setEmail(request.getEmail());

        return ownerRepository.save(existingOwner);
    }

    @Override
    public OwnerResponse addOwner(OwnerRequest ownerRequest) {
        Owner owner = ownerConverter.toOwner(ownerRequest);
        Owner savedOwner = ownerRepository.save(owner);
        OwnerResponse ownerResponse = new OwnerResponse();

        BeanUtils.copyProperties(savedOwner, ownerResponse);
        return ownerResponse;
    }

    @Override
    public OwnerResponse findOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Customer with id: %s not found", id)));
        OwnerResponse ownerResponse = new OwnerResponse();
        BeanUtils.copyProperties(owner, ownerResponse);
        return ownerResponse;
    }
}
