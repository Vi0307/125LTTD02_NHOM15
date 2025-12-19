package com.example.quanlyoto.model;

/**
 * Model class cho Xe - tương ứng với XeDTO từ backend
 */
public class Xe {
    private String maXe;
    private String maHangXe;
    private Integer maND;
    private String maLoaiXe;
    private String bienSo;
    private String dungTich;
    private String soKhung;
    private String mauSac;
    private String hinhAnh;

    // Getters
    public String getMaXe() {
        return maXe;
    }

    public String getMaHangXe() {
        return maHangXe;
    }

    public Integer getMaND() {
        return maND;
    }

    public String getMaLoaiXe() {
        return maLoaiXe;
    }

    public String getBienSo() {
        return bienSo;
    }

    public String getDungTich() {
        return dungTich;
    }

    public String getSoKhung() {
        return soKhung;
    }

    public String getMauSac() {
        return mauSac;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    // Setters
    public void setMaXe(String maXe) {
        this.maXe = maXe;
    }

    public void setMaHangXe(String maHangXe) {
        this.maHangXe = maHangXe;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public void setMaLoaiXe(String maLoaiXe) {
        this.maLoaiXe = maLoaiXe;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public void setDungTich(String dungTich) {
        this.dungTich = dungTich;
    }

    public void setSoKhung(String soKhung) {
        this.soKhung = soKhung;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
