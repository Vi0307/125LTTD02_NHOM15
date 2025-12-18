package com.example.backend.repository;

import com.example.backend.entity.ChiTietLichSuBaoDuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietLichSuBaoDuongRepository extends JpaRepository<ChiTietLichSuBaoDuong, Integer> {
    List<ChiTietLichSuBaoDuong> findByMaLSBD(Integer maLSBD);
    void deleteByMaLSBD(Integer maLSBD);
}
