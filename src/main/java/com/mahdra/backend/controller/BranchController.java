package com.mahdra.backend.controller;

import com.mahdra.backend.dto.BranchRequestDTO;
import com.mahdra.backend.dto.BranchResponseDTO;
import com.mahdra.backend.service.BranchService;
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
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<?> getAllBranches(
            @RequestParam(required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        if (paginated) {
            Page<BranchResponseDTO> page = branchService.getAllBranches(pageable);
            return ResponseEntity.ok(page);
        } else {
            List<BranchResponseDTO> list = branchService.getAllBranches();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(@PathVariable Long id) {
        BranchResponseDTO branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@Valid @RequestBody BranchRequestDTO requestDTO) {
        BranchResponseDTO created = branchService.createBranch(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequestDTO requestDTO) {
        BranchResponseDTO updated = branchService.updateBranch(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BranchResponseDTO>> searchBranches(@RequestParam String keyword) {
        List<BranchResponseDTO> branches = branchService.searchBranches(keyword);
        return ResponseEntity.ok(branches);
    }
}
