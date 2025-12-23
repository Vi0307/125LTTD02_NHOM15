package com.example.backend.dto;

import java.math.BigDecimal;

public class ChiTietDonHangDTO {

    private String maPhuTung;
    private Integer soLuong;
    private BigDecimal giaTien;
    private String tenPhuTung;
    private String hinhAnh;

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

    // Getter và Setter cho tenPhuTung
    public String getTenPhuTung() {
        return tenPhuTung;
    }

    public void setTenPhuTung(String tenPhuTung) {
        this.tenPhuTung = tenPhuTung;
    }

    // Getter và Setter cho hinhAnh
    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
