package com.example.quanlyoto.model;

/**
 * Model class cho Đại Lý - tương ứng với DaiLyDTO từ backend
 */
public class DaiLy {
    private Integer maDaiLy;
    private String tenDaiLy;
    private String diaChi;
    private String soDienThoai;
    private String gioLamViec;
    private String moTa;

    // Getters
    public Integer getMaDaiLy() {
        return maDaiLy;
    }

    public String getTenDaiLy() {
        return tenDaiLy;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getGioLamViec() {
        return gioLamViec;
    }

    public String getMoTa() {
        return moTa;
    }

    // Setters
    public void setMaDaiLy(Integer maDaiLy) {
        this.maDaiLy = maDaiLy;
    }

    public void setTenDaiLy(String tenDaiLy) {
        this.tenDaiLy = tenDaiLy;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setGioLamViec(String gioLamViec) {
        this.gioLamViec = gioLamViec;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
