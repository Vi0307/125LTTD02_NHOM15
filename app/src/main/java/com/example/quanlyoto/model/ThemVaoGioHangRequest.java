package com.example.quanlyoto.model;

import java.io.Serializable;

public class ThemVaoGioHangRequest implements Serializable {
    private String maPhuTung;
    private Integer soLuong;
    private String hinhAnh;
    private java.math.BigDecimal donGia;

    public ThemVaoGioHangRequest() {
    }

    public ThemVaoGioHangRequest(String maPhuTung, Integer soLuong, String hinhAnh, java.math.BigDecimal donGia) {
        this.maPhuTung = maPhuTung;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
        this.donGia = donGia;
    }

    public String getMaPhuTung() {
        return maPhuTung;
    }

    public void setMaPhuTung(String maPhuTung) {
        this.maPhuTung = maPhuTung;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public java.math.BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(java.math.BigDecimal donGia) {
        this.donGia = donGia;
    }
}
