package com.example.backend.controller;

import com.example.backend.dto.PhuongThucVanChuyenDTO;
import com.example.backend.service.VanChuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class VanChuyenController {

    @Autowired
    private VanChuyenService service;

    @GetMapping
    public List<PhuongThucVanChuyenDTO> getAll() {
        return service.getAll();
    }
}

