package com.example.backend.repository;

import com.example.backend.entity.PhuongThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng PHUONG_THUC_THANH_TOAN
 */
@Repository
public interface PhuongThucThanhToanRepository extends JpaRepository<PhuongThucThanhToan, Integer> {
    
    /**
     * Lấy tất cả phương thức thanh toán đang hoạt động
     */
    List<PhuongThucThanhToan> findByTrangThaiTrue();
    
    /**
     * Lấy phương thức thanh toán đầu tiên (mặc định)
     */
    PhuongThucThanhToan findFirstByTrangThaiTrueOrderByMaPTTTAsc();
}
