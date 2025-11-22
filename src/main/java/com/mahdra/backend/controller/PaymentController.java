package com.mahdra.backend.controller;

import com.mahdra.backend.entity.Payment;
import com.mahdra.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<Payment>> getAllPayments(@RequestParam(required = false) Long donorId,
                                                          @RequestParam(required = false) Long classeId,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (donorId != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByDonor(donorId));
        }
        if (classeId != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByClasse(classeId));
        }
        if (startDate != null && endDate != null) {
            return ResponseEntity.ok(paymentService.getPaymentsByDateRange(startDate, endDate));
        }
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) {
        Payment created = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @Valid @RequestBody Payment payment) {
        Payment updated = paymentService.updatePayment(id, payment);
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
