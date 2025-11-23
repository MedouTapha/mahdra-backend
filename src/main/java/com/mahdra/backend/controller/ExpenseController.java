package com.mahdra.backend.controller;

import com.mahdra.backend.dto.ExpenseRequestDTO;
import com.mahdra.backend.dto.ExpenseResponseDTO;
import com.mahdra.backend.dto.ExpenseStatsDTO;
import com.mahdra.backend.service.ExpenseService;
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
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<?> getAllExpenses(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String period,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "false") boolean paginated,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        // Filter by date range
        if (startDate != null && endDate != null) {
            List<ExpenseResponseDTO> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
            return ResponseEntity.ok(expenses);
        }

        // Filter by classId
        if (classId != null) {
            List<ExpenseResponseDTO> expenses = expenseService.getExpensesByClass(classId);
            return ResponseEntity.ok(expenses);
        }

        // Filter by branchId
        if (branchId != null) {
            List<ExpenseResponseDTO> expenses = expenseService.getExpensesByBranch(branchId);
            return ResponseEntity.ok(expenses);
        }

        // Filter by period
        if (period != null) {
            List<ExpenseResponseDTO> expenses = expenseService.getExpensesByPeriod(period);
            return ResponseEntity.ok(expenses);
        }

        // Return all with or without pagination
        if (paginated) {
            Page<ExpenseResponseDTO> page = expenseService.getAllExpenses(pageable);
            return ResponseEntity.ok(page);
        } else {
            List<ExpenseResponseDTO> list = expenseService.getAllExpenses();
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {
        ExpenseResponseDTO expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO requestDTO) {
        ExpenseResponseDTO created = expenseService.createExpense(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {
        ExpenseResponseDTO updated = expenseService.updateExpense(id, requestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<ExpenseStatsDTO> getExpenseStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ExpenseStatsDTO stats = expenseService.getExpenseStats(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/branch/{branchId}")
    public ResponseEntity<Double> getExpenseStatsByBranch(
            @PathVariable Long branchId,
            @RequestParam String period) {
        Double total = expenseService.getExpenseStatsByBranchAndPeriod(branchId, period);
        return ResponseEntity.ok(total);
    }
}
