package com.example.backend.entity;

import jakarta.persistence.*;

/**
 * Entity cho bảng PHUONG_THUC_THANH_TOAN
 */
@Entity
@Table(name = "PHUONG_THUC_THANH_TOAN")
public class PhuongThucThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPTTT;

    private String tenPTTT;
    private String moTa;
    private String icon;
    
    @Column(name = "TrangThai")
    private Boolean trangThai; // 1: Hoạt động, 0: Ngừng hoạt động

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

    public Boolean getTrangThai() {
        return trangThai;
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

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
