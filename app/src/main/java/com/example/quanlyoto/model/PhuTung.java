package com.example.quanlyoto.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PhuTung implements Serializable {
    private String maPhuTung;
    private String maHangXe;
    private String maLoaiXe;
    private String maDanhMuc;
    private String tenPhuTung;
    private BigDecimal giaBan;
    private Integer soLuong;
    private String moTa;
    private String hinhAnh; // URL or name
    private String nhaCC;

    public PhuTung() {
    }

    public PhuTung(String maPhuTung, String maHangXe, String maLoaiXe, String maDanhMuc, String tenPhuTung,
            BigDecimal giaBan, Integer soLuong, String moTa, String hinhAnh, String nhaCC) {
        this.maPhuTung = maPhuTung;
        this.maHangXe = maHangXe;
        this.maLoaiXe = maLoaiXe;
        this.maDanhMuc = maDanhMuc;
        this.tenPhuTung = tenPhuTung;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.nhaCC = nhaCC;
    }

    public String getMaPhuTung() {
        return maPhuTung;
    }

    public void setMaPhuTung(String maPhuTung) {
        this.maPhuTung = maPhuTung;
    }

    public String getMaHangXe() {
        return maHangXe;
    }

    public void setMaHangXe(String maHangXe) {
        this.maHangXe = maHangXe;
    }

    public String getMaLoaiXe() {
        return maLoaiXe;
    }

    public void setMaLoaiXe(String maLoaiXe) {
        this.maLoaiXe = maLoaiXe;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenPhuTung() {
        return tenPhuTung;
    }

    public void setTenPhuTung(String tenPhuTung) {
        this.tenPhuTung = tenPhuTung;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNhaCC() {
        return nhaCC;
    }

    public void setNhaCC(String nhaCC) {
        this.nhaCC = nhaCC;
    }
}
