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
public class ThongBaoDTO {
    private Integer maThongBao;
    private Integer maND;
    private String tieuDe;
    private String noiDung;
    private LocalDateTime ngayTao;
}
