package com.mahdra.backend.mapper;

import com.mahdra.backend.dto.ClassRequestDTO;
import com.mahdra.backend.dto.ClassResponseDTO;
import com.mahdra.backend.entity.Branch;
import com.mahdra.backend.entity.ClassEntity;
import org.springframework.stereotype.Component;

@Component
public class ClassMapper {

    public ClassEntity toEntity(ClassRequestDTO dto, Branch branch) {
        if (dto == null) {
            return null;
        }

        ClassEntity entity = new ClassEntity();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setYearStart(dto.getYearStart());
        entity.setBranch(branch);
        entity.setNiveau(dto.getNiveau());
        entity.setStudentCount(dto.getStudentCount());
        entity.setTeacher(dto.getTeacher());
        entity.setDescription(dto.getDescription());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);
        return entity;
    }

    public ClassResponseDTO toResponseDTO(ClassEntity entity) {
        if (entity == null) {
            return null;
        }

        ClassResponseDTO dto = new ClassResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setYearStart(entity.getYearStart());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setNiveau(entity.getNiveau());
        dto.setStudentCount(entity.getStudentCount());
        dto.setTeacher(entity.getTeacher());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.getActive());

        if (entity.getBranch() != null) {
            dto.setBranchId(entity.getBranch().getId());
            dto.setBranchNomfr(entity.getBranch().getNomfr());
        }

        return dto;
    }

    public void updateEntityFromDTO(ClassRequestDTO dto, ClassEntity entity, Branch branch) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setYearStart(dto.getYearStart());
        entity.setBranch(branch);
        entity.setNiveau(dto.getNiveau());
        entity.setStudentCount(dto.getStudentCount());
        entity.setTeacher(dto.getTeacher());
        entity.setDescription(dto.getDescription());
        if (dto.getActive() != null) {
            entity.setActive(dto.getActive());
        }
    }
}
