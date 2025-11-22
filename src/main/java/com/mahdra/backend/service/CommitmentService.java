package com.mahdra.backend.service;

import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.repository.CommitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommitmentService {

    private final CommitmentRepository commitmentRepository;

    public List<Commitment> getAllCommitments() {
        return commitmentRepository.findAll();
    }

    public Optional<Commitment> getCommitmentById(Long id) {
        return commitmentRepository.findById(id);
    }

    public Commitment createCommitment(Commitment commitment) {
        return commitmentRepository.save(commitment);
    }

    public Commitment updateCommitment(Long id, Commitment commitmentDetails) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commitment not found with id: " + id));

        commitment.setDonor(commitmentDetails.getDonor());
        commitment.setMontant(commitmentDetails.getMontant());
        commitment.setDateEngagement(commitmentDetails.getDateEngagement());
        commitment.setDateEcheance(commitmentDetails.getDateEcheance());
        commitment.setStatut(commitmentDetails.getStatut());
        commitment.setDescription(commitmentDetails.getDescription());

        return commitmentRepository.save(commitment);
    }

    public void deleteCommitment(Long id) {
        commitmentRepository.deleteById(id);
    }

    public List<Commitment> getCommitmentsByDonor(Long donorId) {
        return commitmentRepository.findByDonorId(donorId);
    }

    public List<Commitment> getCommitmentsByStatut(String statut) {
        return commitmentRepository.findByStatut(statut);
    }
}
