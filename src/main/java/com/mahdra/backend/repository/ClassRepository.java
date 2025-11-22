package com.mahdra.backend.repository;

import com.mahdra.backend.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    List<ClassEntity> findByBranchId(Long branchId);

    List<ClassEntity> findByType(String type);

    List<ClassEntity> findByBranchIdAndType(Long branchId, String type);
}
