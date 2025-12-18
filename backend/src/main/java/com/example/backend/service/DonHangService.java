package com.example.backend.service;


import com.example.backend.dto.DonHangDTO;
import com.example.backend.entity.DonHang;
import com.example.backend.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    public List<DonHangDTO> getDonHangByUser(Integer maND) {

        return donHangRepository.findByMaND(maND)
                .stream()
                .map(dh -> {
                    DonHangDTO dto = new DonHangDTO();
                    dto.setMaDH(dh.getMaDH());
                    dto.setNgayDat(dh.getNgayDat());
                    dto.setTongTien(dh.getTongTien());
                    dto.setPhiVanChuyen(dh.getPhiVanChuyen());
                    dto.setTongThanhToan(
                            dh.getTongTien().add(dh.getPhiVanChuyen())
                    );
                    dto.setDiaChiGiao(dh.getDiaChiGiao());
                    dto.setTrangThai(dh.getTrangThai());
                    return dto;
                }).collect(Collectors.toList());
    }
}
