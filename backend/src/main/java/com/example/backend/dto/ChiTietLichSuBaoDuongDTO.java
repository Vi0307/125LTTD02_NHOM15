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
public class ChiTietLichSuBaoDuongDTO {
    private Integer maCTLSBD;
    private Integer maLSBD;
    private Integer maDaiLy;
    private String noiDung;
    private BigDecimal chiPhi;
}
