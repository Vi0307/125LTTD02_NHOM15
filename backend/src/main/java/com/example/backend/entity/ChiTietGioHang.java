package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CHI_TIET_GIO_HANG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietGioHang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTGH")
    private Integer maCTGH;
    
    @Column(name = "MaGioHang", nullable = false)
    private Integer maGioHang;
    
    @Column(name = "MaPhuTung", nullable = false, length = 10)
    private String maPhuTung;
    
    @Column(name = "HinhAnh", nullable = false, length = 255)
    private String hinhAnh;
    
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong = 1;
    
    @Column(name = "DonGia", nullable = false, precision = 18, scale = 0)
    private BigDecimal donGia;
}
