package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NGUOI_DUNG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaND")
    private Integer maND;

    @Column(name = "HoTen", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "Email", unique = true, length = 100)
    private String email;

    @Column(name = "DienThoai", unique = true, length = 20)
    private String dienThoai;

    @Column(name = "NgaySinh", length = 50, nullable = false)
    private String ngaySinh;

    @Column(name = "MatKhau", nullable = false, length = 255)
    private String matKhau;

    @Column(name = "VaiTro", length = 20)
    private String vaiTro; // default 'customer' handled by DB or app logic if creating new

    @Column(name = "NgayBaoDuong")
    private LocalDateTime ngayBaoDuong;

    @Column(name = "SoLanBaoDuong", nullable = false)
    private Integer soLanBaoDuong;

    @Column(name = "IsLocked", nullable = false)
    private Boolean isLocked;
}
