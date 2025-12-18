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
public class BaoDuongDTO {
    private Integer maBD;
    private LocalDateTime ngayBaoDuong;
    private String moTa;
    private Integer maVC;
    private String trangThai;
    private Boolean daNhacNho;
    private Integer maND;
}
