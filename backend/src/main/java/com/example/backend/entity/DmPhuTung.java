package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DM_PHUTUNG")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DmPhuTung {

    @Id
    @Column(name = "MaDanhMuc", length = 10)
    private String maDanhMuc;

    @Column(name = "TenDanhMuc", nullable = false, length = 100)
    private String tenDanhMuc;
}
