package com.mahdra.backend.service;

import com.mahdra.backend.entity.Expense;
import com.mahdra.backend.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        expense.setClassEntity(expenseDetails.getClassEntity());
        expense.setBranch(expenseDetails.getBranch());
        expense.setMontant(expenseDetails.getMontant());
        expense.setDate(expenseDetails.getDate());
        expense.setType(expenseDetails.getType());
        expense.setDescription(expenseDetails.getDescription());
        expense.setPeriod(expenseDetails.getPeriod());
        expense.setBeneficiaire(expenseDetails.getBeneficiaire());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByClass(Long classId) {
        return expenseRepository.findByClassEntityId(classId);
    }

    public List<Expense> getExpensesByBranch(Long branchId) {
        return expenseRepository.findByBranchId(branchId);
    }

    public List<Expense> getExpensesByPeriod(String period) {
        return expenseRepository.findByPeriod(period);
    }
}
