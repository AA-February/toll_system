package com.example.spring_into.repository;

import com.example.spring_into.model.TollPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TollRepository extends JpaRepository<TollPass,Long> {

    Optional<TollPass> findByRegNumberAndCountry(String regNumber, String country);

}
