package com.mahdra.backend.controller;

import com.mahdra.backend.dto.ClassRequestDTO;
import com.mahdra.backend.dto.ClassResponseDTO;
import com.mahdra.backend.service.ClassService;
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
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @GetMapping
    public ResponseEntity<?> getAllClasses(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        // Filter by branchId
        if (branchId != null) {
            List<ClassResponseDTO> classes = classService.getClassesByBranch(branchId);
            return ResponseEntity.ok(classes);
        }

        // Filter by type
        if (type != null) {
            List<ClassResponseDTO> classes = classService.getClassesByType(type);
            return ResponseEntity.ok(classes);
        }

        // Return all with or without pagination
        if (paginated) {
            Page<ClassResponseDTO> page = classService.getAllClasses(pageable);
            return ResponseEntity.ok(page);
        } else {
            List<ClassResponseDTO> list = classService.getAllClasses();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> getClassById(@PathVariable Long id) {
        ClassResponseDTO classe = classService.getClassById(id);
        return ResponseEntity.ok(classe);
    }

    @PostMapping
    public ResponseEntity<ClassResponseDTO> createClass(@Valid @RequestBody ClassRequestDTO requestDTO) {
        ClassResponseDTO created = classService.createClass(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassResponseDTO> updateClass(
            @PathVariable Long id,
            @Valid @RequestBody ClassRequestDTO requestDTO) {
        ClassResponseDTO updated = classService.updateClass(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
