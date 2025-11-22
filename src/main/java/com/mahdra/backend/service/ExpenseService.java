package com.mahdra.backend.service;

import com.mahdra.backend.dto.ExpenseRequestDTO;
import com.mahdra.backend.dto.ExpenseResponseDTO;
import com.mahdra.backend.entity.Branch;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.entity.Expense;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.ExpenseMapper;
import com.mahdra.backend.repository.BranchRepository;
import com.mahdra.backend.repository.ClassRepository;
import com.mahdra.backend.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ClassRepository classRepository;
    private final BranchRepository branchRepository;
    private final ExpenseMapper expenseMapper;

    public Page<ExpenseResponseDTO> getAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable)
                .map(expenseMapper::toResponseDTO);
    }

    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(expenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense", "id", id));
        return expenseMapper.toResponseDTO(expense);
    }

    @Transactional
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO requestDTO) {
        ClassEntity classEntity = null;
        if (requestDTO.getClassId() != null) {
            classEntity = classRepository.findById(requestDTO.getClassId())
                    .orElse(null);
        }

        Branch branch = null;
        if (requestDTO.getBranchId() != null) {
            branch = branchRepository.findById(requestDTO.getBranchId())
                    .orElse(null);
        }

        Expense expense = expenseMapper.toEntity(requestDTO, classEntity, branch);
        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.toResponseDTO(savedExpense);
    }

    @Transactional
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO requestDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dépense", "id", id));

        ClassEntity classEntity = null;
        if (requestDTO.getClassId() != null) {
            classEntity = classRepository.findById(requestDTO.getClassId())
                    .orElse(null);
        }

        Branch branch = null;
        if (requestDTO.getBranchId() != null) {
            branch = branchRepository.findById(requestDTO.getBranchId())
                    .orElse(null);
        }

        expenseMapper.updateEntityFromDTO(requestDTO, expense, classEntity, branch);
        Expense updatedExpense = expenseRepository.save(expense);
        return expenseMapper.toResponseDTO(updatedExpense);
    }

    @Transactional
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dépense", "id", id);
        }
        expenseRepository.deleteById(id);
    }

    public List<ExpenseResponseDTO> getExpensesByClass(Long classId) {
        return expenseRepository.findByClassEntityId(classId).stream()
                .map(expenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDTO> getExpensesByBranch(Long branchId) {
        return expenseRepository.findByBranchId(branchId).stream()
                .map(expenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDTO> getExpensesByPeriod(String period) {
        return expenseRepository.findByPeriod(period).stream()
                .map(expenseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
