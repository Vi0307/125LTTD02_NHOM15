package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DichVuDTO {
    private Integer maDV;
    private Integer maND;
    private String loaiDichVu;
    private String moTa;
    private String trangThai;
    private LocalDateTime ngayTao;
    private Integer maDaiLy;
}
