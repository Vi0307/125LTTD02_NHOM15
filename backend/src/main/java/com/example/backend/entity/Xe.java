package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "XE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Xe {

    @Id
    @Column(name = "MaXe", length = 10)
    private String maXe;

    @Column(name = "MaHangXe", nullable = false, length = 10)
    private String maHangXe;

    @Column(name = "MaND", nullable = false)
    private Integer maND;

    @Column(name = "MaLoaiXe", nullable = false, length = 10)
    private String maLoaiXe;

    @Column(name = "BienSo", nullable = false, length = 20)
    private String bienSo;

    @Column(name = "DungTich", nullable = false, length = 10)
    private String dungTich;

    @Column(name = "SoKhung", nullable = false, length = 50)
    private String soKhung;

    @Column(name = "MauSac", nullable = false, length = 30)
    private String mauSac;

    @Column(name = "HinhAnh", nullable = false, length = 255)
    private String hinhAnh;
}
