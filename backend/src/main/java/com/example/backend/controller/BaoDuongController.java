package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.BaoDuongDTO;
import com.example.backend.dto.ChiTietBaoDuongDTO;
import com.example.backend.service.BaoDuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bao-duong")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BaoDuongController {

    private final BaoDuongService baoDuongService;

    // --- Bao Duong Endpoints ---

    @GetMapping
    public ResponseEntity<ApiResponse<List<BaoDuongDTO>>> getAllBaoDuong() {
        return ResponseEntity.ok(ApiResponse.success(baoDuongService.getAllBaoDuong()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BaoDuongDTO>> getBaoDuongById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ApiResponse.success(baoDuongService.getBaoDuongById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<List<BaoDuongDTO>>> getBaoDuongByNguoiDung(@PathVariable Integer maND) {
        return ResponseEntity.ok(ApiResponse.success(baoDuongService.getBaoDuongByNguoiDung(maND)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BaoDuongDTO>> createBaoDuong(@RequestBody BaoDuongDTO dto) {
        try {
            return ResponseEntity.ok(ApiResponse.success(baoDuongService.createBaoDuong(dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // --- Chi Tiet Bao Duong Endpoints ---

    @GetMapping("/{maBD}/chi-tiet")
    public ResponseEntity<ApiResponse<List<ChiTietBaoDuongDTO>>> getChiTietByBaoDuong(@PathVariable Integer maBD) {
        return ResponseEntity.ok(ApiResponse.success(baoDuongService.getChiTietByBaoDuong(maBD)));
    }
}
