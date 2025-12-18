package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PHU_TUNG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhuTung {

    @Id
    @Column(name = "MaPhuTung", length = 10)
    private String maPhuTung;

    @Column(name = "MaHangXe", nullable = false, length = 10)
    private String maHangXe;

    @Column(name = "MaLoaiXe", nullable = false, length = 10)
    private String maLoaiXe;

    @Column(name = "MaDanhMuc", nullable = false, length = 10)
    private String maDanhMuc;

    @Column(name = "TenPhuTung", nullable = false, length = 100)
    private String tenPhuTung;

    @Column(name = "GiaBan", nullable = false, precision = 18, scale = 0)
    private BigDecimal giaBan;

    @Column(name = "SoLuong", columnDefinition = "INT DEFAULT 1")
    private Integer soLuong;

    @Column(name = "MoTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "HinhAnh", length = 255, nullable = false)
    private String hinhAnh;

    @Column(name = "NhaCC", length = 100, nullable = false)
    private String nhaCC;
}
