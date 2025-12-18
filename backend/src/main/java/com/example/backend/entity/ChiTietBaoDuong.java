package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CHI_TIET_BAO_DUONG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietBaoDuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTBD")
    private Integer maCTBD;

    @Column(name = "MaBD", nullable = false)
    private Integer maBD;

    @Column(name = "MaXe", length = 10)
    private String maXe;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    @Column(name = "SoLanBaoDuong", nullable = false)
    private Integer soLanBaoDuong;
    
    @PrePersist
    protected void onCreate() {
        if (soLanBaoDuong == null) {
            soLanBaoDuong = 0;
        }
    }
}
