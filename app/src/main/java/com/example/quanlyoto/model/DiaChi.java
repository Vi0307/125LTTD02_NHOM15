package com.example.quanlyoto.model;

/**
 * Model class cho Địa Chỉ - tương ứng với DiaChiDTO từ backend
 */
public class DiaChi {
    private Integer maDiaChi;
    private String loaiDiaChi;
    private String hoTenNguoiNhan;
    private String soDienThoai;
    private String tinhThanhPho;
    private String quanHuyen;
    private String phuongXa;
    private String diaChiChiTiet;
    private Boolean macDinh;

    // Getters
    public Integer getMaDiaChi() {
        return maDiaChi;
    }

    public String getLoaiDiaChi() {
        return loaiDiaChi;
    }

    public String getHoTenNguoiNhan() {
        return hoTenNguoiNhan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getTinhThanhPho() {
        return tinhThanhPho;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public String getDiaChiChiTiet() {
        return diaChiChiTiet;
    }

    public Boolean getMacDinh() {
        return macDinh;
    }

    // Setters
    public void setMaDiaChi(Integer maDiaChi) {
        this.maDiaChi = maDiaChi;
    }

    public void setLoaiDiaChi(String loaiDiaChi) {
        this.loaiDiaChi = loaiDiaChi;
    }

    public void setHoTenNguoiNhan(String hoTenNguoiNhan) {
        this.hoTenNguoiNhan = hoTenNguoiNhan;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setTinhThanhPho(String tinhThanhPho) {
        this.tinhThanhPho = tinhThanhPho;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public void setDiaChiChiTiet(String diaChiChiTiet) {
        this.diaChiChiTiet = diaChiChiTiet;
    }

    public void setMacDinh(Boolean macDinh) {
        this.macDinh = macDinh;
    }

    /**
     * Trả về địa chỉ đầy đủ
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (diaChiChiTiet != null && !diaChiChiTiet.isEmpty()) {
            sb.append(diaChiChiTiet);
        }
        if (phuongXa != null && !phuongXa.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(phuongXa);
        }
        if (quanHuyen != null && !quanHuyen.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(quanHuyen);
        }
        if (tinhThanhPho != null && !tinhThanhPho.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(tinhThanhPho);
        }
        return sb.toString();
    }
}
