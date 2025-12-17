package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GioHangDTO {
    
    private Long maGioHang;
    private Long maNguoiDung;
    private Long maSanPham;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;
    
    // Thông tin bổ sung từ sản phẩm (nếu cần)
    private String tenSanPham;
    private String hinhAnh;
}
