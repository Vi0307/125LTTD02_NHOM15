package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HANG_XE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HangXe {

    @Id
    @Column(name = "MaHangXe", length = 10)
    private String maHangXe;

    @Column(name = "TenHangXe", nullable = false, length = 100, unique = true)
    private String tenHangXe;

    @Column(name = "MoTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;
}
