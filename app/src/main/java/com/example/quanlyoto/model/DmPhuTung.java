package com.example.quanlyoto.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DmPhuTung implements Serializable {

    @SerializedName("maDanhMuc")
    private String maDanhMuc;

    @SerializedName("tenDanhMuc")
    private String tenDanhMuc;

    public DmPhuTung() {
    }

    public DmPhuTung(String maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
