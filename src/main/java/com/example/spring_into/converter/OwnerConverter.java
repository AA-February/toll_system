package com.example.spring_into.converter;

import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.model.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerConverter {

   public Owner toOwner(TollRequest tollRequest){
        Owner owner = new Owner();
        owner.setEmail(tollRequest.getEmail());
        owner.setAddress(tollRequest.getAddress());
        owner.setFirstName(tollRequest.getFirstName());
        owner.setLastName(tollRequest.getLastName());

        return owner;
    }

    public Owner toOwner(OwnerRequest request){
       return Owner.builder()
               .email(request.getEmail())
               .firstName(request.getFirstName())
               .lastName(request.getLastName())
               .address(request.getAddress())
               .build();
    }
}
