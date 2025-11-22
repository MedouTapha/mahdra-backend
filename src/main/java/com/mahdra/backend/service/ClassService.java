package com.mahdra.backend.service;

import com.mahdra.backend.entity.ClassEntity;
import com.mahdra.backend.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassService {

    private final ClassRepository classRepository;

    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<ClassEntity> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public ClassEntity createClass(ClassEntity classe) {
        return classRepository.save(classe);
    }

    public ClassEntity updateClass(Long id, ClassEntity classDetails) {
        ClassEntity classe = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));

        classe.setName(classDetails.getName());
        classe.setType(classDetails.getType());
        classe.setYearStart(classDetails.getYearStart());
        classe.setBranch(classDetails.getBranch());
        classe.setNiveau(classDetails.getNiveau());
        classe.setStudentCount(classDetails.getStudentCount());
        classe.setTeacher(classDetails.getTeacher());
        classe.setDescription(classDetails.getDescription());
        classe.setActive(classDetails.getActive());

        return classRepository.save(classe);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public List<ClassEntity> getClassesByBranch(Long branchId) {
        return classRepository.findByBranchId(branchId);
    }

    public List<ClassEntity> getClassesByType(String type) {
        return classRepository.findByType(type);
    }
}
