package com.mahdra.backend.controller;

import com.mahdra.backend.entity.Expense;
import com.mahdra.backend.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestParam(required = false) Long classId,
                                                          @RequestParam(required = false) Long branchId,
                                                          @RequestParam(required = false) String period) {
        if (classId != null) {
            return ResponseEntity.ok(expenseService.getExpensesByClass(classId));
        }
        if (branchId != null) {
            return ResponseEntity.ok(expenseService.getExpensesByBranch(branchId));
        }
        if (period != null) {
            return ResponseEntity.ok(expenseService.getExpensesByPeriod(period));
        }
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense created = expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        Expense updated = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
