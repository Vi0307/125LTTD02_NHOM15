package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DIA_CHI")
public class DiaChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDiaChi")
    private Integer maDiaChi;

    @Column(name = "MaND", nullable = false)
    private Integer maND;

    @Column(name = "LoaiDiaChi", nullable = false)
    private String loaiDiaChi;

    @Column(name = "HoTenNguoiNhan", nullable = false)
    private String hoTenNguoiNhan;

    @Column(name = "SoDienThoai", nullable = false)
    private String soDienThoai;

    @Column(name = "TinhThanhPho", nullable = false)
    private String tinhThanhPho;

    @Column(name = "QuanHuyen", nullable = false)
    private String quanHuyen;

    @Column(name = "PhuongXa", nullable = false)
    private String phuongXa;

    @Column(name = "DiaChiChiTiet", nullable = false)
    private String diaChiChiTiet;

    @Column(name = "MacDinh", nullable = false)
    private Boolean macDinh;

    // ===== Getter & Setter =====

    public Integer getMaDiaChi() {
        return maDiaChi;
    }

    public void setMaDiaChi(Integer maDiaChi) {
        this.maDiaChi = maDiaChi;
    }

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public String getLoaiDiaChi() {
        return loaiDiaChi;
    }

    public void setLoaiDiaChi(String loaiDiaChi) {
        this.loaiDiaChi = loaiDiaChi;
    }

    public String getHoTenNguoiNhan() {
        return hoTenNguoiNhan;
    }

    public void setHoTenNguoiNhan(String hoTenNguoiNhan) {
        this.hoTenNguoiNhan = hoTenNguoiNhan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTinhThanhPho() {
        return tinhThanhPho;
    }

    public void setTinhThanhPho(String tinhThanhPho) {
        this.tinhThanhPho = tinhThanhPho;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getDiaChiChiTiet() {
        return diaChiChiTiet;
    }

    public void setDiaChiChiTiet(String diaChiChiTiet) {
        this.diaChiChiTiet = diaChiChiTiet;
    }

    public Boolean getMacDinh() {
        return macDinh;
    }

    public void setMacDinh(Boolean macDinh) {
        this.macDinh = macDinh;
    }
}
