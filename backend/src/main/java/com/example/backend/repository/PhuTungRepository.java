package com.example.backend.repository;

import com.example.backend.entity.PhuTung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhuTungRepository extends JpaRepository<PhuTung, String> {
    List<PhuTung> findByMaDanhMuc(String maDanhMuc);
    List<PhuTung> findByMaHangXe(String maHangXe);
    List<PhuTung> findByMaLoaiXe(String maLoaiXe);
}
