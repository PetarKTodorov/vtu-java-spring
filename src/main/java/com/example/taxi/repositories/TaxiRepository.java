package com.example.taxi.repositories;

import com.example.taxi.models.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    Optional<Taxi> findByCompanyName(String companyName);

    Optional<List<Taxi>> searchByCompanyNameStartingWith(String companyName);
}
