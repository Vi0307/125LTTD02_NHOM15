package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietGioHangDTO {
    
    private Integer maCTGH;
    private Integer maGioHang;
    private String maPhuTung;
    private String tenPhuTung;  // Thêm tên phụ tùng
    private String hinhAnh;
    private Integer soLuong;
    private BigDecimal donGia;
    
    // Tổng tiền = Số lượng * Đơn giá
    public BigDecimal getTongTien() {
        if (soLuong != null && donGia != null) {
            return donGia.multiply(BigDecimal.valueOf(soLuong));
        }
        return BigDecimal.ZERO;
    }
}
