package com.example.backend.repository;

import com.example.backend.entity.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiXeRepository extends JpaRepository<LoaiXe, String> {
    List<LoaiXe> findByMaHangXe(String maHangXe);
}
