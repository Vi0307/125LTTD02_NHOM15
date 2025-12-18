package com.example.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DonHangDTO {

    private String maDH;
    private LocalDateTime ngayDat;
    private BigDecimal tongTien;
    private BigDecimal phiVanChuyen;
    private BigDecimal tongThanhToan;
    private String diaChiGiao;
    private String trangThai;

    // Getter và Setter cho maDH
    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    // Getter và Setter cho ngayDat
    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    // Getter và Setter cho tongTien
    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    // Getter và Setter cho phiVanChuyen
    public BigDecimal getPhiVanChuyen() {
        return phiVanChuyen;
    }

    public void setPhiVanChuyen(BigDecimal phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }

    // Getter và Setter cho tongThanhToan
    public BigDecimal getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(BigDecimal tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }

    // Getter và Setter cho diaChiGiao
    public String getDiaChiGiao() {
        return diaChiGiao;
    }

    public void setDiaChiGiao(String diaChiGiao) {
        this.diaChiGiao = diaChiGiao;
    }

    // Getter và Setter cho trangThai
    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}

