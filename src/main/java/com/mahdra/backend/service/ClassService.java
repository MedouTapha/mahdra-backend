package com.mahdra.backend.service;

import com.mahdra.backend.dto.ClassRequestDTO;
import com.mahdra.backend.dto.ClassResponseDTO;
import com.mahdra.backend.entity.Branch;
import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.ClassMapper;
import com.mahdra.backend.repository.BranchRepository;
import com.mahdra.backend.repository.ClassRepository;
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
public class ClassService {

    private final ClassRepository classRepository;
    private final BranchRepository branchRepository;
    private final ClassMapper classMapper;

    public Page<ClassResponseDTO> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable)
                .map(classMapper::toResponseDTO);
    }

    public List<ClassResponseDTO> getAllClasses() {
        return classRepository.findAll().stream()
                .map(classMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClassResponseDTO getClassById(Long id) {
        ClassEntity classe = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe", "id", id));
        return classMapper.toResponseDTO(classe);
    }

    @Transactional
    public ClassResponseDTO createClass(ClassRequestDTO requestDTO) {
        Branch branch = branchRepository.findById(requestDTO.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branche", "id", requestDTO.getBranchId()));

        ClassEntity classe = classMapper.toEntity(requestDTO, branch);
        ClassEntity savedClasse = classRepository.save(classe);
        return classMapper.toResponseDTO(savedClasse);
    }

    @Transactional
    public ClassResponseDTO updateClass(Long id, ClassRequestDTO requestDTO) {
        ClassEntity classe = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classe", "id", id));

        Branch branch = branchRepository.findById(requestDTO.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branche", "id", requestDTO.getBranchId()));

        classMapper.updateEntityFromDTO(requestDTO, classe, branch);
        ClassEntity updatedClasse = classRepository.save(classe);
        return classMapper.toResponseDTO(updatedClasse);
    }

    @Transactional
    public void deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            throw new ResourceNotFoundException("Classe", "id", id);
        }
        classRepository.deleteById(id);
    }

    public List<ClassResponseDTO> getClassesByBranch(Long branchId) {
        return classRepository.findByBranchId(branchId).stream()
                .map(classMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ClassResponseDTO> getClassesByType(String type) {
        return classRepository.findByType(type).stream()
                .map(classMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
