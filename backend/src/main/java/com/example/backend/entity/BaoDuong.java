package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAO_DUONG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaoDuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBD")
    private Integer maBD;

    @Column(name = "NgayBaoDuong")
    private LocalDateTime ngayBaoDuong;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    @Column(name = "MaVC")
    private Integer maVC;

    @Column(name = "TrangThai", length = 50)
    private String trangThai;

    @Column(name = "DaNhacNho")
    private Boolean daNhacNho;

    @Column(name = "MaND", nullable = false)
    private Integer maND;

    @PrePersist
    protected void onCreate() {
        if (ngayBaoDuong == null) {
            ngayBaoDuong = LocalDateTime.now();
        }
        if (trangThai == null) {
            trangThai = "Nhắc nhở";
        }
        if (daNhacNho == null) {
            daNhacNho = false;
        }
    }
}
