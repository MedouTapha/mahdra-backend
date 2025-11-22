package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.ExpenseRequestDTO;
import com.mahdra.backend.dto.ExpenseResponseDTO;
import com.mahdra.backend.entity.Branch;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public Expense toEntity(ExpenseRequestDTO dto, ClassEntity classEntity, Branch branch) {
        if (dto == null) {
            return null;
        }

        Expense entity = new Expense();
        entity.setClassEntity(classEntity);
        entity.setBranch(branch);
        entity.setMontant(dto.getMontant());
        entity.setDate(dto.getDate());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setPeriod(dto.getPeriod());
        entity.setBeneficiaire(dto.getBeneficiaire());
        return entity;
    }

    public ExpenseResponseDTO toResponseDTO(Expense entity) {
        if (entity == null) {
            return null;
        }

        ExpenseResponseDTO dto = new ExpenseResponseDTO();
        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setDate(entity.getDate());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        dto.setPeriod(entity.getPeriod());
        dto.setBeneficiaire(entity.getBeneficiaire());

        if (entity.getClassEntity() != null) {
            dto.setClassId(entity.getClassEntity().getId());
            dto.setClassName(entity.getClassEntity().getName());
        }

        if (entity.getBranch() != null) {
            dto.setBranchId(entity.getBranch().getId());
            dto.setBranchNomfr(entity.getBranch().getNomfr());
        }

        return dto;
    }

    public void updateEntityFromDTO(ExpenseRequestDTO dto, Expense entity, ClassEntity classEntity, Branch branch) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setClassEntity(classEntity);
        entity.setBranch(branch);
        entity.setMontant(dto.getMontant());
        entity.setDate(dto.getDate());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setPeriod(dto.getPeriod());
        entity.setBeneficiaire(dto.getBeneficiaire());
    }
}
