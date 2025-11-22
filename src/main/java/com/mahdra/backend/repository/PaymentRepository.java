package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByDonorId(Long donorId);

    List<Payment> findByClasseId(Long classeId);

    List<Payment> findByDonorIdAndClasseId(Long donorId, Long classeId);

    List<Payment> findByDatePaiementBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(p.montant) FROM Payment p WHERE p.donor.id = :donorId")
    Double getTotalPaymentsByDonor(@Param("donorId") Long donorId);

    @Query("SELECT SUM(p.montant) FROM Payment p WHERE p.classe.id = :classeId")
    Double getTotalPaymentsByClasse(@Param("classeId") Long classeId);
}
