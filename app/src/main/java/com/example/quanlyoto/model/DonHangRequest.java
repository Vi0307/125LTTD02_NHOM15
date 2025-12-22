package com.example.quanlyoto.model;

import java.math.BigDecimal;

/**
 * Model class for creating order request
 */
public class DonHangRequest {
    private String tenPhuTung;
    private String hinhAnh;
    private BigDecimal tongTien;
    private BigDecimal phiVanChuyen;
    private String diaChiGiao;
    private Integer maND;
    private Integer maVC;
    private Integer maPTVC;
    private Integer maPTTT;
    private String phuongThucThanhToan;

    public DonHangRequest() {
    }

    // Getters and Setters
    public String getTenPhuTung() {
        return tenPhuTung;
    }

    public void setTenPhuTung(String tenPhuTung) {
        this.tenPhuTung = tenPhuTung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getPhiVanChuyen() {
        return phiVanChuyen;
    }

    public void setPhiVanChuyen(BigDecimal phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }

    public String getDiaChiGiao() {
        return diaChiGiao;
    }

    public void setDiaChiGiao(String diaChiGiao) {
        this.diaChiGiao = diaChiGiao;
    }

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public Integer getMaVC() {
        return maVC;
    }

    public void setMaVC(Integer maVC) {
        this.maVC = maVC;
    }

    public Integer getMaPTVC() {
        return maPTVC;
    }

    public void setMaPTVC(Integer maPTVC) {
        this.maPTVC = maPTVC;
    }

    public Integer getMaPTTT() {
        return maPTTT;
    }

    public void setMaPTTT(Integer maPTTT) {
        this.maPTTT = maPTTT;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}
