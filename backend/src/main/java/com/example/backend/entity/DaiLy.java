package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DAI_LY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaiLy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDaiLy")
    private Integer maDaiLy;

    @Column(name = "TenDaiLy", nullable = false, length = 150)
    private String tenDaiLy;

    @Column(name = "DiaChi", nullable = false, length = 255)
    private String diaChi;

    @Column(name = "SoDienThoai", nullable = false, length = 20)
    private String soDienThoai;

    @Column(name = "GioLamViec", length = 100)
    private String gioLamViec;

    @Column(name = "MoTa", length = 255)
    private String moTa;
}
