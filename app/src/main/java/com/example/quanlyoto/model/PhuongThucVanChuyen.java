package com.example.quanlyoto.model;

import java.math.BigDecimal;

/**
 * Model class cho Phương Thức Vận Chuyển - tương ứng với PhuongThucVanChuyenDTO từ backend
 */
public class PhuongThucVanChuyen {
    private Integer maPTVC;
    private String tenPTVC;
    private BigDecimal giaVanChuyen;
    private Integer soNgayDuKien;

    // Getters
    public Integer getMaPTVC() {
        return maPTVC;
    }

    public String getTenPTVC() {
        return tenPTVC;
    }

    public BigDecimal getGiaVanChuyen() {
        return giaVanChuyen;
    }

    public Integer getSoNgayDuKien() {
        return soNgayDuKien;
    }

    // Setters
    public void setMaPTVC(Integer maPTVC) {
        this.maPTVC = maPTVC;
    }

    public void setTenPTVC(String tenPTVC) {
        this.tenPTVC = tenPTVC;
    }

    public void setGiaVanChuyen(BigDecimal giaVanChuyen) {
        this.giaVanChuyen = giaVanChuyen;
    }

    public void setSoNgayDuKien(Integer soNgayDuKien) {
        this.soNgayDuKien = soNgayDuKien;
    }

    /**
     * Trả về mô tả phương thức vận chuyển
     */
    public String getDescription() {
        if (tenPTVC != null && soNgayDuKien != null) {
            return tenPTVC + " (" + soNgayDuKien + " ngày)";
        }
        return tenPTVC != null ? tenPTVC : "";
    }
}
