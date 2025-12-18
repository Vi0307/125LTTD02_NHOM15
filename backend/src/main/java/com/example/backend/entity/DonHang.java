package com.example.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "DON_HANG")
public class DonHang {

    @Id
    @Column(name = "MaDH", length = 20)
    private String maDH;

    @Column(name = "NgayDat")
    private LocalDateTime ngayDat;

    @Column(name = "TenPhuTung", nullable = false)
    private String tenPhuTung;

    @Column(name = "HinhAnh", nullable = false)
    private String hinhAnh;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    @Column(name = "PhiVanChuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "DiaChiGiao", nullable = false)
    private String diaChiGiao;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "MaND", nullable = false)
    private Integer maND;

    @Column(name = "MaVC")
    private Integer maVC;

    @Column(name = "NgayNhanDuKien")
    private LocalDateTime ngayNhanDuKien;

    @Column(name = "MaPTVC")
    private Integer maPTVC;

    @Column(name = "PhuongThucThanhToan")
    private String phuongThucThanhToan;

    @Column(name = "TrangThai")
    private String trangThai;

    /* ================= GETTER / SETTER ================= */

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
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

    public LocalDateTime getNgayNhanDuKien() {
        return ngayNhanDuKien;
    }

    public void setNgayNhanDuKien(LocalDateTime ngayNhanDuKien) {
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
