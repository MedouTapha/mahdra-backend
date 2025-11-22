package com.mahdra.backend.controller;

import com.mahdra.backend.dto.CommitmentRequestDTO;
import com.mahdra.backend.dto.CommitmentResponseDTO;
import com.mahdra.backend.service.CommitmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<?> getAllCommitments(
            @RequestParam(required = false) Long donorId,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        // Filter by donorId
        if (donorId != null) {
            List<CommitmentResponseDTO> commitments = commitmentService.getCommitmentsByDonor(donorId);
            return ResponseEntity.ok(commitments);
        }

        // Filter by statut
        if (statut != null) {
            List<CommitmentResponseDTO> commitments = commitmentService.getCommitmentsByStatut(statut);
            return ResponseEntity.ok(commitments);
        }

        // Return all with or without pagination
        if (paginated) {
            Page<CommitmentResponseDTO> page = commitmentService.getAllCommitments(pageable);
            return ResponseEntity.ok(page);
        } else {
            List<CommitmentResponseDTO> list = commitmentService.getAllCommitments();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommitmentResponseDTO> getCommitmentById(@PathVariable Long id) {
        CommitmentResponseDTO commitment = commitmentService.getCommitmentById(id);
        return ResponseEntity.ok(commitment);
    }

    @PostMapping
    public ResponseEntity<CommitmentResponseDTO> createCommitment(@Valid @RequestBody CommitmentRequestDTO requestDTO) {
        CommitmentResponseDTO created = commitmentService.createCommitment(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommitmentResponseDTO> updateCommitment(
            @PathVariable Long id,
            @Valid @RequestBody CommitmentRequestDTO requestDTO) {
        CommitmentResponseDTO updated = commitmentService.updateCommitment(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommitment(@PathVariable Long id) {
        commitmentService.deleteCommitment(id);
        return ResponseEntity.noContent().build();
    }
}
