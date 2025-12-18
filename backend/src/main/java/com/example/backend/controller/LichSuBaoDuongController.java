package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ChiTietLichSuBaoDuongDTO;
import com.example.backend.dto.LichSuBaoDuongDTO;
import com.example.backend.service.LichSuBaoDuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lich-su-bao-duong")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LichSuBaoDuongController {

    private final LichSuBaoDuongService lichSuBaoDuongService;

    // --- Lich Su Bao Duong Endpoints ---

    @GetMapping
    public ResponseEntity<ApiResponse<List<LichSuBaoDuongDTO>>> getAllLichSu() {
        return ResponseEntity.ok(ApiResponse.success(lichSuBaoDuongService.getAllLichSu()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LichSuBaoDuongDTO>> getLichSuById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ApiResponse.success(lichSuBaoDuongService.getLichSuById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<List<LichSuBaoDuongDTO>>> getLichSuByNguoiDung(@PathVariable Integer maND) {
        return ResponseEntity.ok(ApiResponse.success(lichSuBaoDuongService.getLichSuByNguoiDung(maND)));
    }

    // --- Chi Tiet Lich Su Bao Duong Endpoints ---

    @GetMapping("/{maLSBD}/chi-tiet")
    public ResponseEntity<ApiResponse<List<ChiTietLichSuBaoDuongDTO>>> getChiTietByLichSu(@PathVariable Integer maLSBD) {
        return ResponseEntity.ok(ApiResponse.success(lichSuBaoDuongService.getChiTietByLichSu(maLSBD)));
    }


}
