package com.example.quanlyoto.model;

import java.io.Serializable;
import java.util.Date;

public class GioHangDTO implements Serializable {
    private Integer maGioHang;
    private Integer nguoiDungId;
    private Date ngayTao;

    public GioHangDTO() {
    }

    public Integer getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(Integer maGioHang) {
        this.maGioHang = maGioHang;
    }

    public Integer getNguoiDungId() {
        return nguoiDungId;
    }

    public void setNguoiDungId(Integer nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
