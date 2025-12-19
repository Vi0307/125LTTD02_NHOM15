package com.example.backend.service;

import com.example.backend.dto.VoucherDTO;
import com.example.backend.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository repo;

    public List<VoucherDTO> getVoucherByUser(Integer maND) {
        // Sử dụng findByMaND để lấy danh sách đầy đủ (cả hết hạn/khác trạng thái)
        return repo.findByMaND(maND)
                .stream()
                .map(v -> {
                    VoucherDTO dto = new VoucherDTO();
                    dto.setMaND(v.getMaND());
                    dto.setMaVC(v.getMaVC());
                    dto.setLoaiVoucher(v.getLoaiVoucher());
                    dto.setHanSuDung(v.getHanSuDung());
                    dto.setTrangThai(v.getTrangThai());
                    return dto;
                }).toList();
    }

    public List<VoucherDTO> getAllVouchers() {
        return repo.findAll()
                .stream()
                .map(v -> {
                    VoucherDTO dto = new VoucherDTO();
                    dto.setMaND(v.getMaND());
                    dto.setMaVC(v.getMaVC());
                    dto.setLoaiVoucher(v.getLoaiVoucher());
                    dto.setHanSuDung(v.getHanSuDung());
                    dto.setTrangThai(v.getTrangThai());
                    return dto;
                }).toList();
    }
}

