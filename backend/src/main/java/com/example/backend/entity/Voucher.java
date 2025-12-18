package com.example.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "VOUCHER")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maVC;

    private Integer maND;
    private String loaiVoucher;
    private LocalDate hanSuDung;
    private String trangThai;
}
