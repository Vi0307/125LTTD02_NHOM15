package com.example.backend.repository;

import com.example.backend.entity.ChiTietGioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, Integer> {
    
    // Lấy tất cả chi tiết theo mã giỏ hàng
    List<ChiTietGioHang> findByMaGioHang(Integer maGioHang);
    
    // Tìm chi tiết theo giỏ hàng và phụ tùng
    Optional<ChiTietGioHang> findByMaGioHangAndMaPhuTung(Integer maGioHang, String maPhuTung);
    
    // Kiểm tra phụ tùng đã có trong giỏ hàng chưa
    boolean existsByMaGioHangAndMaPhuTung(Integer maGioHang, String maPhuTung);
    
    // Đếm số lượng sản phẩm trong giỏ hàng
    long countByMaGioHang(Integer maGioHang);
    
    // Xóa tất cả chi tiết theo mã giỏ hàng
    @Modifying
    void deleteByMaGioHang(Integer maGioHang);
    
    // Xóa chi tiết theo giỏ hàng và phụ tùng
    @Modifying
    void deleteByMaGioHangAndMaPhuTung(Integer maGioHang, String maPhuTung);
}
