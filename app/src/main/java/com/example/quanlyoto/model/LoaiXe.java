package com.example.quanlyoto.model;

/**
 * Model class cho Loại Xe
 */
public class LoaiXe {
    private String maLoaiXe;
    private String maHangXe;
    private String tenLoaiXe;
    private String moTa;

    // Getters
    public String getMaLoaiXe() {
        return maLoaiXe;
    }

    public String getMaHangXe() {
        return maHangXe;
    }

    public String getTenLoaiXe() {
        return tenLoaiXe;
    }

    public String getMoTa() {
        return moTa;
    }

    // Setters
    public void setMaLoaiXe(String maLoaiXe) {
        this.maLoaiXe = maLoaiXe;
    }

    public void setMaHangXe(String maHangXe) {
        this.maHangXe = maHangXe;
    }

    public void setTenLoaiXe(String tenLoaiXe) {
        this.tenLoaiXe = tenLoaiXe;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
