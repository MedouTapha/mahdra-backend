package com.mahdra.backend.controller;

import com.mahdra.backend.dto.DonorRequestDTO;
import com.mahdra.backend.dto.DonorResponseDTO;
import com.mahdra.backend.service.DonorService;
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
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @GetMapping
    public ResponseEntity<?> getAllDonors(
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        // Filter by actif
        if (actif != null && actif) {
            List<DonorResponseDTO> donors = donorService.getActiveDonors();
            return ResponseEntity.ok(donors);
        }

        // Filter by type
        if (type != null) {
            List<DonorResponseDTO> donors = donorService.getDonorsByType(type);
            return ResponseEntity.ok(donors);
        }

        // Return all with or without pagination
        if (paginated) {
            Page<DonorResponseDTO> page = donorService.getAllDonors(pageable);
            return ResponseEntity.ok(page);
        } else {
            List<DonorResponseDTO> list = donorService.getAllDonors();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorResponseDTO> getDonorById(@PathVariable Long id) {
        DonorResponseDTO donor = donorService.getDonorById(id);
        return ResponseEntity.ok(donor);
    }

    @PostMapping
    public ResponseEntity<DonorResponseDTO> createDonor(@Valid @RequestBody DonorRequestDTO requestDTO) {
        DonorResponseDTO created = donorService.createDonor(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonorResponseDTO> updateDonor(
            @PathVariable Long id,
            @Valid @RequestBody DonorRequestDTO requestDTO) {
        DonorResponseDTO updated = donorService.updateDonor(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}
