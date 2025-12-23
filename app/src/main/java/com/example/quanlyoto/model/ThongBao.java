package com.example.quanlyoto.model;

import java.io.Serializable;

public class ThongBao implements Serializable {
    private Integer maThongBao;
    private Integer maND;
    private String tieuDe;
    private String noiDung;
    private String ngayTao;

    public ThongBao() {
    }

    public ThongBao(Integer maThongBao, Integer maND, String tieuDe, String noiDung, String ngayTao) {
        this.maThongBao = maThongBao;
        this.maND = maND;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
    }

    public Integer getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(Integer maThongBao) {
        this.maThongBao = maThongBao;
    }

    public Integer getMaND() {
        return maND;
    }

    public void setMaND(Integer maND) {
        this.maND = maND;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
