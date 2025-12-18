package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CHI_TIET_LS_BAO_DUONG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietLichSuBaoDuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTLSBD")
    private Integer maCTLSBD;

    @Column(name = "MaLSBD", nullable = false)
    private Integer maLSBD;

    @Column(name = "MaDaiLy", nullable = false)
    private Integer maDaiLy;

    @Column(name = "NoiDung", nullable = false, length = 255)
    private String noiDung;

    @Column(name = "ChiPhi", columnDefinition = "DECIMAL(18,0)")
    private BigDecimal chiPhi;
    
    @PrePersist
    protected void onCreate() {
        if (chiPhi == null) {
             chiPhi = BigDecimal.ZERO;
        }
    }
}
