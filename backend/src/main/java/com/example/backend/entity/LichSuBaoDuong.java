package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LICH_SU_BAO_DUONG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LichSuBaoDuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLSBD")
    private Integer maLSBD;

    @Column(name = "NgayThucHien", nullable = false)
    private LocalDateTime ngayThucHien;

    @Column(name = "MaND", nullable = false)
    private Integer maND;

    @Column(name = "MaXe", nullable = false, length = 10)
    private String maXe;

    @Column(name = "MaDaiLy", nullable = false)
    private Integer maDaiLy;

    @PrePersist
    protected void onCreate() {
        if (ngayThucHien == null) {
            ngayThucHien = LocalDateTime.now();
        }
    }
}
