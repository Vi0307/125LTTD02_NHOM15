package com.example.backend.repository;

import com.example.backend.entity.LichSuBaoDuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichSuBaoDuongRepository extends JpaRepository<LichSuBaoDuong, Integer> {
    List<LichSuBaoDuong> findByMaND(Integer maND);
}
