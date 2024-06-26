package com.example.spring_into.converter;

import com.example.spring_into.config.PasswordEncoderConfig;
import com.example.spring_into.dto.OwnerRequest;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OwnerConverter {

   @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

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
               .password(passwordEncoderConfig.passwordEncoder().encode(request.getPassword()))
               .build();
    }
}
