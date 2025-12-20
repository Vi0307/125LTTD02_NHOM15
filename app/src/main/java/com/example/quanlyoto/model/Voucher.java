package com.example.quanlyoto.model;

import java.io.Serializable;

/**
 * Model class cho Voucher - tương ứng với VoucherDTO từ backend
 */
public class Voucher implements Serializable {
    private Integer maVC;
    private Integer maND;
    private String loaiVoucher;
    private String hanSuDung;
    private String trangThai;

    public Voucher() {
    }

    public Voucher(Integer maVC, Integer maND, String loaiVoucher, String hanSuDung, String trangThai) {
        this.maVC = maVC;
        this.maND = maND;
        this.loaiVoucher = loaiVoucher;
        this.hanSuDung = hanSuDung;
        this.trangThai = trangThai;
    }

    public Integer getMaVC() {
        return maVC;
    }

    public void setMaVC(Integer maVC) {
        this.maVC = maVC;
    }

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    /**
     * Trả về mô tả voucher
     */
    public String getDescription() {
        if (loaiVoucher != null && hanSuDung != null) {
            return loaiVoucher + " - HSD: " + hanSuDung;
        }
        return loaiVoucher != null ? loaiVoucher : "";
    }
}
