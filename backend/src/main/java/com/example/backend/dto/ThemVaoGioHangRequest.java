package com.example.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemVaoGioHangRequest {
    
    private String maPhuTung;
    private String hinhAnh;
    private Integer soLuong;
    private BigDecimal donGia;
}
