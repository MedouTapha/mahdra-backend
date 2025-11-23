package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByClassEntityId(Long classId);

    List<Expense> findByBranchId(Long branchId);

    List<Expense> findByPeriod(String period);

    List<Expense> findByClassEntityIdAndPeriod(Long classId, String period);

    // Filtrer par plage de dates
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Statistiques pour expenses
    @Query("SELECT SUM(e.montant) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    Double sumByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(e.montant) FROM Expense e WHERE e.branch.id = :branchId AND e.period = :period")
    Double sumByBranchAndPeriod(@Param("branchId") Long branchId, @Param("period") String period);

    @Query("SELECT e.type, SUM(e.montant) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate GROUP BY e.type")
    List<Object[]> sumByCategoryAndDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
