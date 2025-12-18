package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhuTungDTO {
    private String maPhuTung;
    private String maHangXe;
    private String maLoaiXe;
    private String maDanhMuc;
    private String tenPhuTung;
    private BigDecimal giaBan;
    private Integer soLuong;
    private String moTa;
    private String hinhAnh;
    private String nhaCC;
}
