package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietBaoDuongDTO {
    private Integer maCTBD;
    private Integer maBD;
    private String maXe;
    private String moTa;
    private Integer soLanBaoDuong;
}
