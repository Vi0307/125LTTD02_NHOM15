package com.example.quanlyoto.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DonHang implements Serializable {
    @SerializedName("maDH")
    private String maDH;

    @SerializedName("ngayDat")
    private String ngayDat; // Using String to simplify json parsing, usually formatted date

    @SerializedName("tenPhuTung")
    private String tenPhuTung;

    @SerializedName("hinhAnh")
    private String hinhAnh;

    @SerializedName("tongTien")
    private BigDecimal tongTien;

    @SerializedName("phiVanChuyen")
    private BigDecimal phiVanChuyen;

    @SerializedName("diaChiGiao")
    private String diaChiGiao;

    @SerializedName("ghiChu")
    private String ghiChu;

    @SerializedName("maND")
    private Integer maND;

    @SerializedName("maVC")
    private Integer maVC;

    @SerializedName("ngayNhanDuKien")
    private String ngayNhanDuKien;

    @SerializedName("maPTVC")
    private Integer maPTVC;

    @SerializedName("phuongThucThanhToan")
    private String phuongThucThanhToan;

    @SerializedName("trangThai")
    private String trangThai;

    // Getters and Setters

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
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

    public String getNgayNhanDuKien() {
        return ngayNhanDuKien;
    }

    public void setNgayNhanDuKien(String ngayNhanDuKien) {
        this.ngayNhanDuKien = ngayNhanDuKien;
    }

    public Integer getMaPTVC() {
        return maPTVC;
    }

    public void setMaPTVC(Integer maPTVC) {
        this.maPTVC = maPTVC;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
