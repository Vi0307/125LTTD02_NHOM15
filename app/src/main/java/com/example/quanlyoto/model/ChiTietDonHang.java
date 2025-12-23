package com.example.quanlyoto.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.math.BigDecimal;

public class ChiTietDonHang implements Serializable {
    @SerializedName("maCTDH")
    private Integer maCTDH;

    @SerializedName("maDH")
    private String maDH;

    @SerializedName("maPhuTung")
    private String maPhuTung;

    @SerializedName("soLuong")
    private Integer soLuong;

    @SerializedName("giaTien")
    private BigDecimal giaTien;

    // Additional fields returned by backend
    @SerializedName("tenPhuTung")
    private String tenPhuTung;

    @SerializedName("hinhAnh")
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
