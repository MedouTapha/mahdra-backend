package com.mahdra.backend.service;

import com.mahdra.backend.entity.Payment;
import com.mahdra.backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        payment.setDonor(paymentDetails.getDonor());
        payment.setCommitment(paymentDetails.getCommitment());
        payment.setClasse(paymentDetails.getClasse());
        payment.setMontant(paymentDetails.getMontant());
        payment.setDatePaiement(paymentDetails.getDatePaiement());
        payment.setModePaiement(paymentDetails.getModePaiement());
        payment.setReference(paymentDetails.getReference());
        payment.setRemarque(paymentDetails.getRemarque());

        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<Payment> getPaymentsByDonor(Long donorId) {
        return paymentRepository.findByDonorId(donorId);
    }

    public List<Payment> getPaymentsByClasse(Long classeId) {
        return paymentRepository.findByClasseId(classeId);
    }

    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByDatePaiementBetween(startDate, endDate);
    }

    public Double getTotalPaymentsByDonor(Long donorId) {
        return paymentRepository.getTotalPaymentsByDonor(donorId);
    }

    public Double getTotalPaymentsByClasse(Long classeId) {
        return paymentRepository.getTotalPaymentsByClasse(classeId);
    }
}
