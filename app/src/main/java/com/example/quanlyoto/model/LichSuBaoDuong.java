package com.example.quanlyoto.model;

/**
 * Model class cho Lịch sử bảo dưỡng - tương ứng với LichSuBaoDuongDTO từ
 * backend
 */
public class LichSuBaoDuong {
    private Integer maLSBD;
    private String ngayThucHien;
    private Integer maND;
    private String maXe;
    private Integer maDaiLy;

    // Getters
    public Integer getMaLSBD() {
        return maLSBD;
    }

    public String getNgayThucHien() {
        return ngayThucHien;
    }

    public Integer getMaND() {
        return maND;
    }

    public String getMaXe() {
        return maXe;
    }

    public Integer getMaDaiLy() {
        return maDaiLy;
    }

    // Setters
    public void setMaLSBD(Integer maLSBD) {
        this.maLSBD = maLSBD;
    }

    public void setNgayThucHien(String ngayThucHien) {
        this.ngayThucHien = ngayThucHien;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public void setMaXe(String maXe) {
        this.maXe = maXe;
    }

    public void setMaDaiLy(Integer maDaiLy) {
        this.maDaiLy = maDaiLy;
    }
}
