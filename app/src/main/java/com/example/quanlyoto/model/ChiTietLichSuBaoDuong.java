package com.example.quanlyoto.model;

import java.math.BigDecimal;

/**
 * Model class cho Chi tiết lịch sử bảo dưỡng - tương ứng với
 * ChiTietLichSuBaoDuongDTO từ backend
 */
public class ChiTietLichSuBaoDuong {
    private Integer maCTLSBD;
    private Integer maLSBD;
    private Integer maDaiLy;
    private String noiDung;
    private BigDecimal chiPhi;

    // Getters
    public Integer getMaCTLSBD() {
        return maCTLSBD;
    }

    public Integer getMaLSBD() {
        return maLSBD;
    }

    public Integer getMaDaiLy() {
        return maDaiLy;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public BigDecimal getChiPhi() {
        return chiPhi;
    }

    // Setters
    public void setMaCTLSBD(Integer maCTLSBD) {
        this.maCTLSBD = maCTLSBD;
    }

    public void setMaLSBD(Integer maLSBD) {
        this.maLSBD = maLSBD;
    }

    public void setMaDaiLy(Integer maDaiLy) {
        this.maDaiLy = maDaiLy;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setChiPhi(BigDecimal chiPhi) {
        this.chiPhi = chiPhi;
    }
}
