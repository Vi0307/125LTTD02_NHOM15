package com.example.backend.controller;

import com.example.backend.entity.HangXe;
import com.example.backend.service.HangXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hangxe")
@CrossOrigin(origins = "*")
public class HangXeController {

    @Autowired
    private HangXeService hangXeService;

    @GetMapping
    public List<HangXe> getAllHangXe() {
        return hangXeService.getAllHangXe();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HangXe> getHangXeById(@PathVariable String id) {
        return hangXeService.getHangXeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
