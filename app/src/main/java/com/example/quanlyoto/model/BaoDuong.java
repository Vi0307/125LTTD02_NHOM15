package com.example.quanlyoto.model;

/**
 * Model class cho Bảo Dưỡng - tương ứng với BaoDuongDTO từ backend
 */
public class BaoDuong {
    private Integer maBD;
    private String ngayBaoDuong;
    private String moTa;
    private Integer maVC;
    private String trangThai;
    private Boolean daNhacNho;
    private Integer maND;

    // Getters
    public Integer getMaBD() {
        return maBD;
    }

    public String getNgayBaoDuong() {
        return ngayBaoDuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public Integer getMaVC() {
        return maVC;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public Boolean getDaNhacNho() {
        return daNhacNho;
    }

    public Integer getMaND() {
        return maND;
    }

    // Setters
    public void setMaBD(Integer maBD) {
        this.maBD = maBD;
    }

    public void setNgayBaoDuong(String ngayBaoDuong) {
        this.ngayBaoDuong = ngayBaoDuong;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setMaVC(Integer maVC) {
        this.maVC = maVC;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setDaNhacNho(Boolean daNhacNho) {
        this.daNhacNho = daNhacNho;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }
}
