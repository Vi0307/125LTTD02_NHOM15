package com.example.quanlyoto.model;

/**
 * Model class cho Người Dùng - tương ứng với NguoiDungDTO từ backend
 */
public class NguoiDung {
    private Integer maND;
    private String hoTen;
    private String email;
    private String dienThoai;
    private String ngaySinh;
    private String vaiTro;
    private String ngayBaoDuong;
    private Integer soLanBaoDuong;
    private Boolean isLocked;

    // Getters
    public Integer getMaND() {
        return maND;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getEmail() {
        return email;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public String getNgayBaoDuong() {
        return ngayBaoDuong;
    }

    public Integer getSoLanBaoDuong() {
        return soLanBaoDuong;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    // Setters
    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public void setNgayBaoDuong(String ngayBaoDuong) {
        this.ngayBaoDuong = ngayBaoDuong;
    }

    public void setSoLanBaoDuong(Integer soLanBaoDuong) {
        this.soLanBaoDuong = soLanBaoDuong;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }
}
