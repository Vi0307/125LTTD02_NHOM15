package com.example.backend.dto;

import java.math.BigDecimal;

public class PhuongThucVanChuyenDTO {
    private Integer maPTVC;
    private String tenPTVC;
    private BigDecimal giaVanChuyen;
    private Integer soNgayDuKien;

    // Getter và Setter cho maPTVC
    public Integer getMaPTVC() {
        return maPTVC;
    }

    public void setMaPTVC(Integer maPTVC) {
        this.maPTVC = maPTVC;
    }

    // Getter và Setter cho tenPTVC
    public String getTenPTVC() {
        return tenPTVC;
    }

    public void setTenPTVC(String tenPTVC) {
        this.tenPTVC = tenPTVC;
    }

    // Getter và Setter cho giaVanChuyen
    public BigDecimal getGiaVanChuyen() {
        return giaVanChuyen;
    }

    public void setGiaVanChuyen(BigDecimal giaVanChuyen) {
        this.giaVanChuyen = giaVanChuyen;
    }

    // Getter và Setter cho soNgayDuKien
    public Integer getSoNgayDuKien() {
        return soNgayDuKien;
    }

    public void setSoNgayDuKien(Integer soNgayDuKien) {
        this.soNgayDuKien = soNgayDuKien;
    }
}
