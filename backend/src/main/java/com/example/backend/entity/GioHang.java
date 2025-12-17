package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "GIO_HANG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GioHang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MA_GIO_HANG")
    private Long maGioHang;
    
    @Column(name = "MA_NGUOI_DUNG")
    private Long maNguoiDung;
    
    @Column(name = "MA_SAN_PHAM")
    private Long maSanPham;
    
    @Column(name = "SO_LUONG")
    private Integer soLuong;
    
    @Column(name = "DON_GIA", precision = 18, scale = 2)
    private BigDecimal donGia;
    
    @Column(name = "THANH_TIEN", precision = 18, scale = 2)
    private BigDecimal thanhTien;
}
