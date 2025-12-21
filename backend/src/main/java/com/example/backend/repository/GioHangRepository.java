package com.example.backend.repository;

import com.example.backend.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Integer> {
    
    Optional<GioHang> findByMaND(Integer maND);
    
    // Tìm tất cả giỏ hàng (để xử lý lỗi duplicate)
    java.util.List<GioHang> findAllByMaND(Integer maND);
    
    boolean existsByMaND(Integer maND);
}
