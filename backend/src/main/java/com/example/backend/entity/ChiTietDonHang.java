package com.example.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CHI_TIET_DON_HANG")
public class ChiTietDonHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTDH")
    private Integer maCTDH;

    @Column(name = "MaDH", nullable = false)
    private String maDH;

    @Column(name = "MaPhuTung", nullable = false)
    private String maPhuTung;

    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong;

    @Column(name = "GiaTien", nullable = false)
    private BigDecimal giaTien;

    // Getter và Setter cho maCTDH
    public Integer getMaCTDH() {
        return maCTDH;
    }

    public void setMaCTDH(Integer maCTDH) {
        this.maCTDH = maCTDH;
    }

    // Getter và Setter cho maDH
    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    // Getter và Setter cho maPhuTung
    public String getMaPhuTung() {
        return maPhuTung;
    }

    public void setMaPhuTung(String maPhuTung) {
        this.maPhuTung = maPhuTung;
    }

    // Getter và Setter cho soLuong
    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    // Getter và Setter cho giaTien
    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
    }
}

