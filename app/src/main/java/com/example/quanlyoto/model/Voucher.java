package com.example.quanlyoto.model;

/**
 * Model class cho Voucher - tương ứng với VoucherDTO từ backend
 */
public class Voucher {
    private Integer maND;
    private Integer maVC;
    private String loaiVoucher;
    private String hanSuDung; // String format for easier parsing from JSON
    private String trangThai;

    public Voucher() {
    }

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
