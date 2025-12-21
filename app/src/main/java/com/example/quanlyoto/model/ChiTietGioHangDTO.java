package com.example.quanlyoto.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChiTietGioHangDTO implements Serializable {

    private Integer maCTGH;
    private Integer maGioHang;
    private String maPhuTung;
    private String tenPhuTung;
    private String hinhAnh;
    private Integer soLuong;
    private BigDecimal donGia;

    public ChiTietGioHangDTO() {
    }

    public Integer getMaCTGH() {
        return maCTGH;
    }

    public void setMaCTGH(Integer maCTGH) {
        this.maCTGH = maCTGH;
    }

    public Integer getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(Integer maGioHang) {
        this.maGioHang = maGioHang;
    }

    public String getMaPhuTung() {
        return maPhuTung;
    }

    public void setMaPhuTung(String maPhuTung) {
        this.maPhuTung = maPhuTung;
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

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }
}
