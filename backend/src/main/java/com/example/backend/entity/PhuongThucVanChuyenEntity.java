package com.example.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PHUONG_THUC_VAN_CHUYEN")
public class PhuongThucVanChuyenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPTVC;

    private String tenPTVC;
    private BigDecimal giaVanChuyen;
    private Integer soNgayDuKien;
    private String moTa;
}

