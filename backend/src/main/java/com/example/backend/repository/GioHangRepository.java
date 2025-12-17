package com.example.backend.repository;

import com.example.backend.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Long> {
    
    List<GioHang> findByMaNguoiDung(Long maNguoiDung);
    
    GioHang findByMaNguoiDungAndMaSanPham(Long maNguoiDung, Long maSanPham);
    
    void deleteByMaNguoiDung(Long maNguoiDung);
    
    void deleteByMaNguoiDungAndMaSanPham(Long maNguoiDung, Long maSanPham);
}
