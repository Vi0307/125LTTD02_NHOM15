package com.example.backend.repository;

import com.example.backend.entity.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, Integer> {
    List<DichVu> findByMaND(Integer maND);
    List<DichVu> findByMaDaiLy(Integer maDaiLy);
}
