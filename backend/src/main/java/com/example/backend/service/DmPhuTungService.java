package com.example.backend.service;

import com.example.backend.entity.DmPhuTung;
import com.example.backend.repository.DmPhuTungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DmPhuTungService {

    @Autowired
    private DmPhuTungRepository dmPhuTungRepository;

    public List<DmPhuTung> getAllDanhMuc() {
        return dmPhuTungRepository.findAll();
    }

    public Optional<DmPhuTung> getDanhMucById(String id) {
        return dmPhuTungRepository.findById(id);
    }
}
