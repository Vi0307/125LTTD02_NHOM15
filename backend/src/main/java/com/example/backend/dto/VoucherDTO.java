package com.example.backend.dto;

import java.time.LocalDate;

public class VoucherDTO {
    private Integer maVC;
    private String loaiVoucher;
    private LocalDate hanSuDung;

    // Getter và Setter cho maVC
    public Integer getMaVC() {
        return maVC;
    }

    public void setMaVC(Integer maVC) {
        this.maVC = maVC;
    }

    // Getter và Setter cho loaiVoucher
    public String getLoaiVoucher() {
        return loaiVoucher;
    }

    public void setLoaiVoucher(String loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    // Getter và Setter cho hanSuDung
    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }
}

