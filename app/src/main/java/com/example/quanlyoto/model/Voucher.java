package com.example.quanlyoto.model;

/**
 * /**
 * Model class cho Voucher - tương ứng với VoucherDTO từ backend
 */
public class Voucher {
    private Integer maND;
    private Integer maVC;
    private String loaiVoucher;

    private String hanSuDung; // Receiving as String (ISO-8601) or specific format from JSON

    private String hanSuDung; // String format for easier parsing from JSON

    // Getters

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
        // Setters
        public void setMaND(Integer maND) {
            this.maND = maND;
        }

        public void setMaVC(Integer maVC) {
            this.maVC = maVC;
        }

        public void setLoaiVoucher(String loaiVoucher) {
            this.loaiVoucher = loaiVoucher;
        }

        public void setHanSuDung(String hanSuDung) {
            this.hanSuDung = hanSuDung;
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
