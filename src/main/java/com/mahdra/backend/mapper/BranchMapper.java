package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.BranchRequestDTO;
import com.mahdra.backend.dto.BranchResponseDTO;
import com.mahdra.backend.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public Branch toEntity(BranchRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Branch branch = new Branch();
        branch.setNomfr(dto.getNomfr());
        branch.setNomar(dto.getNomar());
        return branch;
    }

    public BranchResponseDTO toResponseDTO(Branch entity) {
        if (entity == null) {
            return null;
        }

        BranchResponseDTO dto = new BranchResponseDTO();
        dto.setId(entity.getId());
        dto.setNomfr(entity.getNomfr());
        dto.setNomar(entity.getNomar());
        dto.setClassCount(entity.getClasses() != null ? entity.getClasses().size() : 0);
        return dto;
    }

    public void updateEntityFromDTO(BranchRequestDTO dto, Branch entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setNomfr(dto.getNomfr());
        entity.setNomar(dto.getNomar());
    }
}
