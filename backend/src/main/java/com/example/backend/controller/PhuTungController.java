package com.example.backend.controller;

import com.example.backend.entity.PhuTung;
import com.example.backend.service.PhuTungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phutung")
@CrossOrigin(origins = "*")
public class PhuTungController {

    @Autowired
    private PhuTungService phuTungService;

    @GetMapping
    public List<PhuTung> getAllPhuTung() {
        return phuTungService.getAllPhuTung();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuTung> getPhuTungById(@PathVariable String id) {
        return phuTungService.getPhuTungById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/danhmuc/{maDanhMuc}")
    public List<PhuTung> getPhuTungByDanhMuc(@PathVariable String maDanhMuc) {
        return phuTungService.getPhuTungByDanhMuc(maDanhMuc);
    }

    @GetMapping("/hangxe/{maHangXe}")
    public List<PhuTung> getPhuTungByHangXe(@PathVariable String maHangXe) {
        return phuTungService.getPhuTungByHangXe(maHangXe);
    }

    @GetMapping("/loaixe/{maLoaiXe}")
    public List<PhuTung> getPhuTungByLoaiXe(@PathVariable String maLoaiXe) {
        return phuTungService.getPhuTungByLoaiXe(maLoaiXe);
    }
}
