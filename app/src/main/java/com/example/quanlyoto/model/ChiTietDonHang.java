package com.example.quanlyoto.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;

public class ChiTietDonHang implements Serializable {
    @SerializedName("MaCTDH")
    private Integer maCTDH;

    @SerializedName("MaDH")
    private String maDH;

    @SerializedName("MaPhuTung")
    private String maPhuTung;

    @SerializedName("SoLuong")
    private Integer soLuong;

    @SerializedName("GiaTien")
    private BigDecimal giaTien;

    // Additional fields likely returned by a JOIN query on the backend
    @SerializedName("TenPhuTung")
    private String tenPhuTung;

    @SerializedName("HinhAnh")
    private String hinhAnh;

    public Integer getMaCTDH() {
        return maCTDH;
    }

    public void setMaCTDH(Integer maCTDH) {
        this.maCTDH = maCTDH;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
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

    public BigDecimal getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(BigDecimal giaTien) {
        this.giaTien = giaTien;
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
}
