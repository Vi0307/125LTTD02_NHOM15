package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XeDTO {
    private String maXe;
    private String maHangXe;
    private Integer maND;
    private String maLoaiXe;
    private String bienSo;
    private String dungTich;
    private String soKhung;
    private String mauSac;
    private String hinhAnh;
}
