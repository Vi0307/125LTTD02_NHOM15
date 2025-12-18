package com.example.backend.service;

import com.example.backend.entity.LoaiXe;
import com.example.backend.repository.LoaiXeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiXeService {

    @Autowired
    private LoaiXeRepository loaiXeRepository;

    public List<LoaiXe> getAllLoaiXe() {
        return loaiXeRepository.findAll();
    }

    public Optional<LoaiXe> getLoaiXeById(String id) {
        return loaiXeRepository.findById(id);
    }
    
    public List<LoaiXe> getLoaiXeByHangXe(String maHangXe) {
        return loaiXeRepository.findByMaHangXe(maHangXe);
    }
}
