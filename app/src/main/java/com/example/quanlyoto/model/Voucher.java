package com.example.quanlyoto.model;

import java.time.LocalDate;

public class Voucher {
    private Integer maND;
    private Integer maVC;
    private String loaiVoucher;
    private String hanSuDung; // Receiving as String (ISO-8601) or specific format from JSON

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public Integer getMaVC() {
        return maVC;
    }

    public void setMaVC(Integer maVC) {
        this.maVC = maVC;
    }

    public String getLoaiVoucher() {
        return loaiVoucher;
    }

    public void setLoaiVoucher(String loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    private String trangThai;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
