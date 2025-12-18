package com.example.backend.service;

import com.example.backend.entity.PhuTung;
import com.example.backend.repository.PhuTungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhuTungService {

    @Autowired
    private PhuTungRepository phuTungRepository;

    public List<PhuTung> getAllPhuTung() {
        return phuTungRepository.findAll();
    }

    public Optional<PhuTung> getPhuTungById(String id) {
        return phuTungRepository.findById(id);
    }

    public List<PhuTung> getPhuTungByDanhMuc(String maDanhMuc) {
        return phuTungRepository.findByMaDanhMuc(maDanhMuc);
    }
    
    public List<PhuTung> getPhuTungByHangXe(String maHangXe) {
        return phuTungRepository.findByMaHangXe(maHangXe);
    }

    public List<PhuTung> getPhuTungByLoaiXe(String maLoaiXe) {
        return phuTungRepository.findByMaLoaiXe(maLoaiXe);
    }
}
