package com.mahdra.backend.service;

import com.mahdra.backend.dto.PaymentRequestDTO;
import com.mahdra.backend.dto.PaymentResponseDTO;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.entity.Donor;
import com.mahdra.backend.entity.Payment;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.PaymentMapper;
import com.mahdra.backend.repository.ClassRepository;
import com.mahdra.backend.repository.CommitmentRepository;
import com.mahdra.backend.repository.DonorRepository;
import com.mahdra.backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final DonorRepository donorRepository;
    private final CommitmentRepository commitmentRepository;
    private final ClassRepository classRepository;
    private final PaymentMapper paymentMapper;

    public Page<PaymentResponseDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(paymentMapper::toResponseDTO);
    }

    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "id", id));
        return paymentMapper.toResponseDTO(payment);
    }

    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO) {
        Donor donor = donorRepository.findById(requestDTO.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", requestDTO.getDonorId()));

        Commitment commitment = null;
        if (requestDTO.getCommitmentId() != null) {
            commitment = commitmentRepository.findById(requestDTO.getCommitmentId())
                    .orElse(null);
        }

        ClassEntity classe = null;
        if (requestDTO.getClasseId() != null) {
            classe = classRepository.findById(requestDTO.getClasseId())
                    .orElse(null);
        }

        Payment payment = paymentMapper.toEntity(requestDTO, donor, commitment, classe);
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponseDTO(savedPayment);
    }

    @Transactional
    public PaymentResponseDTO updatePayment(Long id, PaymentRequestDTO requestDTO) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", "id", id));

        Donor donor = donorRepository.findById(requestDTO.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donateur", "id", requestDTO.getDonorId()));

        Commitment commitment = null;
        if (requestDTO.getCommitmentId() != null) {
            commitment = commitmentRepository.findById(requestDTO.getCommitmentId())
                    .orElse(null);
        }

        ClassEntity classe = null;
        if (requestDTO.getClasseId() != null) {
            classe = classRepository.findById(requestDTO.getClasseId())
                    .orElse(null);
        }

        paymentMapper.updateEntityFromDTO(requestDTO, payment, donor, commitment, classe);
        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponseDTO(updatedPayment);
    }

    @Transactional
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement", "id", id);
        }
        paymentRepository.deleteById(id);
    }

    public List<PaymentResponseDTO> getPaymentsByDonor(Long donorId) {
        return paymentRepository.findByDonorId(donorId).stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentResponseDTO> getPaymentsByClasse(Long classeId) {
        return paymentRepository.findByClasseId(classeId).stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentResponseDTO> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByDatePaiementBetween(startDate, endDate).stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Double getTotalPaymentsByDonor(Long donorId) {
        return paymentRepository.getTotalPaymentsByDonor(donorId);
    }

    public Double getTotalPaymentsByClasse(Long classeId) {
        return paymentRepository.getTotalPaymentsByClasse(classeId);
    }
}
