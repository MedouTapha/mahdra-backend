package com.mahdra.backend.repository;

import com.mahdra.backend.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> findByNomfrContainingIgnoreCase(String nomfr);

    List<Branch> findByNomarContaining(String nomar);
}
