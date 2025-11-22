package com.mahdra.backend.controller;

import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.service.ClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ClassEntity>> getAllClasses(@RequestParam(required = false) Long branchId,
                                                            @RequestParam(required = false) String type) {
        if (branchId != null) {
            return ResponseEntity.ok(classService.getClassesByBranch(branchId));
        }
        if (type != null) {
            return ResponseEntity.ok(classService.getClassesByType(type));
        }
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassEntity> getClassById(@PathVariable Long id) {
        return classService.getClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassEntity> createClass(@Valid @RequestBody ClassEntity classe) {
        ClassEntity created = classService.createClass(classe);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassEntity> updateClass(@PathVariable Long id, @Valid @RequestBody ClassEntity classe) {
        ClassEntity updated = classService.updateClass(id, classe);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
