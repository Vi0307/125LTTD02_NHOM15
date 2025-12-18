package com.example.backend.controller;

import com.example.backend.entity.LoaiXe;
import com.example.backend.service.LoaiXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loaixe")
@CrossOrigin(origins = "*")
public class LoaiXeController {

    @Autowired
    private LoaiXeService loaiXeService;

    @GetMapping
    public List<LoaiXe> getAllLoaiXe() {
        return loaiXeService.getAllLoaiXe();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoaiXe> getLoaiXeById(@PathVariable String id) {
        return loaiXeService.getLoaiXeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hangxe/{maHangXe}")
    public List<LoaiXe> getLoaiXeByHangXe(@PathVariable String maHangXe) {
        return loaiXeService.getLoaiXeByHangXe(maHangXe);
    }
}
