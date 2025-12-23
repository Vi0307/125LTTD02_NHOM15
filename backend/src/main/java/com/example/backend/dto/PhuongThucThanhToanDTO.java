package com.example.backend.dto;

/**
 * DTO for Payment Method / Phương thức thanh toán
 */
public class PhuongThucThanhToanDTO {
    private Integer maPTTT;
    private String tenPTTT;
    private String moTa;
    private String icon;
    private Boolean macDinh;

    public PhuongThucThanhToanDTO() {}

    public PhuongThucThanhToanDTO(Integer maPTTT, String tenPTTT, String moTa, String icon, Boolean macDinh) {
        this.maPTTT = maPTTT;
        this.tenPTTT = tenPTTT;
        this.moTa = moTa;
        this.icon = icon;
        this.macDinh = macDinh;
    }

    // Getters and Setters
    public Integer getMaPTTT() {
        return maPTTT;
    }

    public void setMaPTTT(Integer maPTTT) {
        this.maPTTT = maPTTT;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getMacDinh() {
        return macDinh;
    }

    public void setMacDinh(Boolean macDinh) {
        this.macDinh = macDinh;
    }
}
