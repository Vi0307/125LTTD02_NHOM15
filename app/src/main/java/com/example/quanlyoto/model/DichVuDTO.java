package com.example.quanlyoto.model;

public class DichVuDTO {
    private Integer maDV;
    private Integer maND;
    private String loaiDichVu;
    private String moTa;
    private String trangThai;
    private String ngayTao; // Format: yyyy-MM-dd'T'HH:mm:ss
    private Integer maDaiLy;

    public DichVuDTO(Integer maND, String loaiDichVu, String moTa, Integer maDaiLy, String ngayTao) {
        this.maND = maND;
        this.loaiDichVu = loaiDichVu;
        this.moTa = moTa;
        this.maDaiLy = maDaiLy;
        this.ngayTao = ngayTao;
        this.trangThai = "Chưa hoàn thành";
    }

    public Integer getMaDV() {
        return maDV;
    }

    public void setMaDV(Integer maDV) {
        this.maDV = maDV;
    }

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public String getLoaiDichVu() {
        return loaiDichVu;
    }

    public void setLoaiDichVu(String loaiDichVu) {
        this.loaiDichVu = loaiDichVu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getMaDaiLy() {
        return maDaiLy;
    }

    public void setMaDaiLy(Integer maDaiLy) {
        this.maDaiLy = maDaiLy;
    }
}
