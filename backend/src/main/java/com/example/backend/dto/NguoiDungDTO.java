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
public class NguoiDungDTO {
    private Integer maND;
    private String hoTen;
    private String email;
    private String dienThoai;
    private String ngaySinh;
    private String vaiTro;
    private LocalDateTime ngayBaoDuong;
    private Integer soLanBaoDuong;
    private Boolean isLocked;
}
