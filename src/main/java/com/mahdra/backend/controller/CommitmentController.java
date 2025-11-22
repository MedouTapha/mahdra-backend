package com.mahdra.backend.controller;

import com.mahdra.backend.entity.Commitment;
import com.mahdra.backend.service.CommitmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commitments")
@RequiredArgsConstructor
public class CommitmentController {

    private final CommitmentService commitmentService;

    @GetMapping
    public ResponseEntity<List<Commitment>> getAllCommitments(@RequestParam(required = false) Long donorId,
                                                                @RequestParam(required = false) String statut) {
        if (donorId != null) {
            return ResponseEntity.ok(commitmentService.getCommitmentsByDonor(donorId));
        }
        if (statut != null) {
            return ResponseEntity.ok(commitmentService.getCommitmentsByStatut(statut));
        }
        return ResponseEntity.ok(commitmentService.getAllCommitments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commitment> getCommitmentById(@PathVariable Long id) {
        return commitmentService.getCommitmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Commitment> createCommitment(@Valid @RequestBody Commitment commitment) {
        Commitment created = commitmentService.createCommitment(commitment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commitment> updateCommitment(@PathVariable Long id, @Valid @RequestBody Commitment commitment) {
        Commitment updated = commitmentService.updateCommitment(id, commitment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommitment(@PathVariable Long id) {
        commitmentService.deleteCommitment(id);
        return ResponseEntity.noContent().build();
    }
}
