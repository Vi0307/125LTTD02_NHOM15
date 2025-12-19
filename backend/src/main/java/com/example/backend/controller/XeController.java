package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.XeDTO;
import com.example.backend.service.XeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class XeController {

    private final XeService xeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<XeDTO>>> getAllXe() {
        return ResponseEntity.ok(ApiResponse.success(xeService.getAllXe()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<XeDTO>> getXeById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(ApiResponse.success(xeService.getXeById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // Lấy xe theo người dùng
    @GetMapping("/nguoidung/{maND}")
    public ResponseEntity<ApiResponse<List<XeDTO>>> getXeByNguoiDung(@PathVariable Integer maND) {
        try {
            return ResponseEntity.ok(ApiResponse.success(xeService.getXeByNguoiDung(maND)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
