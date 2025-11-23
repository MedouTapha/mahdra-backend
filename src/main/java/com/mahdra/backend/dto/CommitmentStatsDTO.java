package com.mahdra.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommitmentStatsDTO {
    private Double totalAmount;
    private Integer totalCount;
    private Map<String, StatutStats> byStatut = new HashMap<>();
    private Integer year;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatutStats {
        private Long count;
        private Double totalAmount;
    }
}
