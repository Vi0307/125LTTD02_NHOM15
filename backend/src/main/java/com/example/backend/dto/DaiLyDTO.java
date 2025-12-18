package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaiLyDTO {
    private Integer maDaiLy;
    private String tenDaiLy;
    private String diaChi;
    private String soDienThoai;
    private String gioLamViec;
    private String moTa;
}
