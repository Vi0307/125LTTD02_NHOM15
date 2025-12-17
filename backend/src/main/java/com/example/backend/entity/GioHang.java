package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GIO_HANG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GioHang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGioHang")
    private Integer maGioHang;
    
    @Column(name = "MaND", nullable = false)
    private Integer maND;
}
