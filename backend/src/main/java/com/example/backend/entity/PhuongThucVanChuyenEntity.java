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

    // Getter và Setter cho maPTVC
    public Integer getMaPTVC() {
        return maPTVC;
    }

    public void setMaPTVC(Integer maPTVC) {
        this.maPTVC = maPTVC;
    }

    // Getter và Setter cho tenPTVC
    public String getTenPTVC() {
        return tenPTVC;
    }

    public void setTenPTVC(String tenPTVC) {
        this.tenPTVC = tenPTVC;
    }

    // Getter và Setter cho giaVanChuyen
    public BigDecimal getGiaVanChuyen() {
        return giaVanChuyen;
    }

    public void setGiaVanChuyen(BigDecimal giaVanChuyen) {
        this.giaVanChuyen = giaVanChuyen;
    }

    // Getter và Setter cho soNgayDuKien
    public Integer getSoNgayDuKien() {
        return soNgayDuKien;
    }

    public void setSoNgayDuKien(Integer soNgayDuKien) {
        this.soNgayDuKien = soNgayDuKien;
    }

    // Getter và Setter cho moTa
    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}

