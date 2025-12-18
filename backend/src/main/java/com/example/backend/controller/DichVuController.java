package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.DichVuDTO;
import com.example.backend.service.DichVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dich-vu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DichVuController {

    private final DichVuService dichVuService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DichVuDTO>>> getAllDichVu() {
        return ResponseEntity.ok(ApiResponse.success(dichVuService.getAllDichVu()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DichVuDTO>> getDichVuById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ApiResponse.success(dichVuService.getDichVuById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<List<DichVuDTO>>> getDichVuByNguoiDung(@PathVariable Integer maND) {
        return ResponseEntity.ok(ApiResponse.success(dichVuService.getDichVuByNguoiDung(maND)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DichVuDTO>> createDichVu(@RequestBody DichVuDTO dichVuDTO) {
        try {
            return ResponseEntity.ok(ApiResponse.success(dichVuService.createDichVu(dichVuDTO)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
