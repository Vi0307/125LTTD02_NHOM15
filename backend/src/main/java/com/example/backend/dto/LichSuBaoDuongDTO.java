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
public class LichSuBaoDuongDTO {
    private Integer maLSBD;
    private LocalDateTime ngayThucHien;
    private Integer maND;
    private String maXe;
    private Integer maDaiLy;
}
