package com.example.quanlyoto.model;

/**
 * Model class cho Phương thức thanh toán
 */
public class PhuongThucThanhToan {
    private Integer maPTTT;
    private String tenPTTT;
    private String moTa;
    private String icon;
    private Boolean macDinh;

    public PhuongThucThanhToan() {}

    // Getters
    public Integer getMaPTTT() {
        return maPTTT;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getIcon() {
        return icon;
    }

    public Boolean getMacDinh() {
        return macDinh;
    }

    // Setters
    public void setMaPTTT(Integer maPTTT) {
        this.maPTTT = maPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setMacDinh(Boolean macDinh) {
        this.macDinh = macDinh;
    }
}
