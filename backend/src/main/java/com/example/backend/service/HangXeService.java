package com.example.backend.service;

import com.example.backend.entity.HangXe;
import com.example.backend.repository.HangXeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HangXeService {

    @Autowired
    private HangXeRepository hangXeRepository;

    public List<HangXe> getAllHangXe() {
        return hangXeRepository.findAll();
    }

    public Optional<HangXe> getHangXeById(String id) {
        return hangXeRepository.findById(id);
    }
}
