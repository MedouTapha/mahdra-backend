package com.mahdra.backend.service;

import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DonorService {

    private final DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Optional<Donor> getDonorById(Long id) {
        return donorRepository.findById(id);
    }

    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public Donor updateDonor(Long id, Donor donorDetails) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));

        donor.setNom(donorDetails.getNom());
        donor.setPrenom(donorDetails.getPrenom());
        donor.setEmail(donorDetails.getEmail());
        donor.setTelephone(donorDetails.getTelephone());
        donor.setAdresse(donorDetails.getAdresse());
        donor.setType(donorDetails.getType());
        donor.setActif(donorDetails.getActif());

        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    public List<Donor> getActiveDonors() {
        return donorRepository.findByActif(true);
    }

    public List<Donor> getDonorsByType(String type) {
        return donorRepository.findByType(type);
    }
}
