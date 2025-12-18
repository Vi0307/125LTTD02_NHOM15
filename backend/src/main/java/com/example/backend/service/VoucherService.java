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
        return repo.getVoucherHopLe(maND)
                .stream()
                .map(v -> {
                    VoucherDTO dto = new VoucherDTO();
                    dto.setMaVC(v.getMaVC());
                    dto.setLoaiVoucher(v.getLoaiVoucher());
                    dto.setHanSuDung(v.getHanSuDung());
                    return dto;
                }).toList();
    }
}

