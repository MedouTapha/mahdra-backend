package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {

    List<Commitment> findByDonorId(Long donorId);

    List<Commitment> findByStatut(String statut);

    List<Commitment> findByDonorIdAndStatut(Long donorId, String statut);

    // Statistiques pour commitments
    @Query("SELECT SUM(c.montant) FROM Commitment c WHERE YEAR(c.dateEngagement) = :year")
    Double sumByYear(@Param("year") Integer year);

    @Query("SELECT c.statut, COUNT(c), SUM(c.montant) FROM Commitment c WHERE YEAR(c.dateEngagement) = :year GROUP BY c.statut")
    List<Object[]> statsByYear(@Param("year") Integer year);

    @Query("SELECT COUNT(c), SUM(c.montant) FROM Commitment c WHERE c.donor.id = :donorId")
    Object[] statsByDonor(@Param("donorId") Long donorId);

    @Query("SELECT c FROM Commitment c WHERE c.dateEngagement BETWEEN :startDate AND :endDate")
    List<Commitment> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
