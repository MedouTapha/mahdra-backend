package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseStatsDTO {
    private Double totalAmount;
    private Integer totalCount;
    private Map<String, Double> byCategory = new HashMap<>();
    private String period;
}
