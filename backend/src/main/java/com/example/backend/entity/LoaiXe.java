package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LOAI_XE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoaiXe {

    @Id
    @Column(name = "MaLoaiXe", length = 10)
    private String maLoaiXe;

    @Column(name = "MaHangXe", nullable = false, length = 10)
    private String maHangXe;

    @Column(name = "TenLoaiXe", nullable = false, length = 50)
    private String tenLoaiXe;

    @Column(name = "MoTa", length = 255)
    private String moTa;
}
