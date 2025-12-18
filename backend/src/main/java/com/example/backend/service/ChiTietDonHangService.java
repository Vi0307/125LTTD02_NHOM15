package com.example.backend.service;

import com.example.backend.dto.ChiTietDonHangDTO;
import com.example.backend.repository.ChiTietDonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietDonHangService {

    @Autowired
    private ChiTietDonHangRepository repository;

    public List<ChiTietDonHangDTO> getChiTietByMaDH(String maDH) {

        return repository.findByMaDH(maDH)
                .stream()
                .map(ct -> {
                    ChiTietDonHangDTO dto = new ChiTietDonHangDTO();
                    dto.setMaPhuTung(ct.getMaPhuTung());
                    dto.setSoLuong(ct.getSoLuong());
                    dto.setGiaTien(ct.getGiaTien());
                    return dto;
                }).collect(Collectors.toList());
    }
}
