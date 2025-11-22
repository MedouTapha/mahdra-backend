package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {

    List<Commitment> findByDonorId(Long donorId);

    List<Commitment> findByStatut(String statut);

    List<Commitment> findByDonorIdAndStatut(Long donorId, String statut);
}
