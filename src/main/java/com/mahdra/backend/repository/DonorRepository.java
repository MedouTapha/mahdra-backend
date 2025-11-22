package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByActif(Boolean actif);

    List<Donor> findByType(String type);

    List<Donor> findByTypeAndActif(String type, Boolean actif);

    Optional<Donor> findByEmail(String email);

    List<Donor> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
