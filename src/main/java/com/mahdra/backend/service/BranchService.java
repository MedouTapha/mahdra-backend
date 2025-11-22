package com.mahdra.backend.service;

import com.mahdra.backend.dto.BranchRequestDTO;
import com.mahdra.backend.dto.BranchResponseDTO;
import com.mahdra.backend.entity.Branch;
import com.mahdra.backend.exception.ResourceNotFoundException;
import com.mahdra.backend.mapper.BranchMapper;
import com.mahdra.backend.repository.BranchRepository;
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
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public Page<BranchResponseDTO> getAllBranches(Pageable pageable) {
        return branchRepository.findAll(pageable)
                .map(branchMapper::toResponseDTO);
    }

    public List<BranchResponseDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BranchResponseDTO getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branche", "id", id));
        return branchMapper.toResponseDTO(branch);
    }

    @Transactional
    public BranchResponseDTO createBranch(BranchRequestDTO requestDTO) {
        Branch branch = branchMapper.toEntity(requestDTO);
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toResponseDTO(savedBranch);
    }

    @Transactional
    public BranchResponseDTO updateBranch(Long id, BranchRequestDTO requestDTO) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branche", "id", id));

        branchMapper.updateEntityFromDTO(requestDTO, branch);
        Branch updatedBranch = branchRepository.save(branch);
        return branchMapper.toResponseDTO(updatedBranch);
    }

    @Transactional
    public void deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Branche", "id", id);
        }
        branchRepository.deleteById(id);
    }

    public List<BranchResponseDTO> searchBranches(String keyword) {
        return branchRepository.findByNomfrContainingIgnoreCase(keyword).stream()
                .map(branchMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
