package com.mahdra.backend.controller;

import com.mahdra.backend.dto.PaymentRequestDTO;
import com.mahdra.backend.dto.PaymentResponseDTO;
import com.mahdra.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getAllPayments(
            @RequestParam(required = false) Long donorId,
            @RequestParam(required = false) Long classeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        // Filter by donorId
        if (donorId != null) {
            List<PaymentResponseDTO> payments = paymentService.getPaymentsByDonor(donorId);
            return ResponseEntity.ok(payments);
        }

        // Filter by classeId
        if (classeId != null) {
            List<PaymentResponseDTO> payments = paymentService.getPaymentsByClasse(classeId);
            return ResponseEntity.ok(payments);
        }

        // Filter by date range
        if (startDate != null && endDate != null) {
            List<PaymentResponseDTO> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
            return ResponseEntity.ok(payments);
        }

        // Return all with or without pagination
        if (paginated) {
            Page<PaymentResponseDTO> page = paymentService.getAllPayments(pageable);
            return ResponseEntity.ok(page);
        } else {
            List<PaymentResponseDTO> list = paymentService.getAllPayments();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        PaymentResponseDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        PaymentResponseDTO created = paymentService.createPayment(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDTO requestDTO) {
        PaymentResponseDTO updated = paymentService.updatePayment(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total/donor/{donorId}")
    public ResponseEntity<Double> getTotalPaymentsByDonor(@PathVariable Long donorId) {
        Double total = paymentService.getTotalPaymentsByDonor(donorId);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }

    @GetMapping("/total/classe/{classeId}")
    public ResponseEntity<Double> getTotalPaymentsByClasse(@PathVariable Long classeId) {
        Double total = paymentService.getTotalPaymentsByClasse(classeId);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
}
