package com.example.backend.controller;

import com.example.backend.dto.DiaChiDTO;
import com.example.backend.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@CrossOrigin
public class DiaChiController {

    @Autowired
    private DiaChiService diaChiService;

    // Lấy danh sách địa chỉ theo user
    @GetMapping("/{maND}")
    public List<DiaChiDTO> getDiaChi(@PathVariable Integer maND) {
        return diaChiService.getDiaChiByUser(maND);
    }

    // Lấy địa chỉ mặc định
    @GetMapping("/{maND}/default")
    public DiaChiDTO getDiaChiMacDinh(@PathVariable Integer maND) {
        return diaChiService.getDiaChiMacDinh(maND);
    }
}