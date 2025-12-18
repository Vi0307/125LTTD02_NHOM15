package com.example.backend.repository;

import com.example.backend.entity.ChiTietBaoDuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietBaoDuongRepository extends JpaRepository<ChiTietBaoDuong, Integer> {
    List<ChiTietBaoDuong> findByMaBD(Integer maBD);
    void deleteByMaBD(Integer maBD);
}
