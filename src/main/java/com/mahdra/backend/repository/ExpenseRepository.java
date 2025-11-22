package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByClassEntityId(Long classId);

    List<Expense> findByBranchId(Long branchId);

    List<Expense> findByPeriod(String period);

    List<Expense> findByClassEntityIdAndPeriod(Long classId, String period);
}
