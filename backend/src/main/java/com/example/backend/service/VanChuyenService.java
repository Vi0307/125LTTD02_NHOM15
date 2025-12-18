package com.example.backend.service;

import com.example.backend.dto.PhuongThucVanChuyenDTO;
import com.example.backend.repository.PhuongThucVanChuyenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VanChuyenService {

    @Autowired
    private PhuongThucVanChuyenRepository repo;

    public List<PhuongThucVanChuyenDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(vc -> {
                    PhuongThucVanChuyenDTO dto = new PhuongThucVanChuyenDTO();
                    dto.setMaPTVC(vc.getMaPTVC());
                    dto.setTenPTVC(vc.getTenPTVC());
                    dto.setGiaVanChuyen(vc.getGiaVanChuyen());
                    dto.setSoNgayDuKien(vc.getSoNgayDuKien());
                    return dto;
                }).toList();
    }
}

