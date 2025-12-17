package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.GioHangDTO;
import com.example.backend.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gio-hang")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GioHangController {
    
    private final GioHangService gioHangService;
    
    // Lấy tất cả giỏ hàng
    @GetMapping
    public ResponseEntity<ApiResponse<List<GioHangDTO>>> getAllGioHang() {
        List<GioHangDTO> list = gioHangService.getAllGioHang();
        return ResponseEntity.ok(ApiResponse.success(list));
    }
    
    // Lấy giỏ hàng theo ID
    @GetMapping("/{maGioHang}")
    public ResponseEntity<ApiResponse<GioHangDTO>> getGioHangById(
            @PathVariable Integer maGioHang) {
        try {
            GioHangDTO dto = gioHangService.getGioHangById(maGioHang);
            return ResponseEntity.ok(ApiResponse.success(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Lấy giỏ hàng theo người dùng
    @GetMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<GioHangDTO>> getGioHangByNguoiDung(
            @PathVariable Integer maND) {
        try {
            GioHangDTO dto = gioHangService.getGioHangByNguoiDung(maND);
            return ResponseEntity.ok(ApiResponse.success(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Tạo giỏ hàng mới
    @PostMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<GioHangDTO>> taoGioHang(
            @PathVariable Integer maND) {
        try {
            GioHangDTO dto = gioHangService.taoGioHang(maND);
            return ResponseEntity.ok(ApiResponse.success("Tạo giỏ hàng thành công", dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Lấy hoặc tạo giỏ hàng
    @PostMapping("/get-or-create/{maND}")
    public ResponseEntity<ApiResponse<GioHangDTO>> getOrCreateGioHang(
            @PathVariable Integer maND) {
        try {
            GioHangDTO dto = gioHangService.getOrCreateGioHang(maND);
            return ResponseEntity.ok(ApiResponse.success(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Xóa giỏ hàng
    @DeleteMapping("/{maGioHang}")
    public ResponseEntity<ApiResponse<Void>> xoaGioHang(
            @PathVariable Integer maGioHang) {
        try {
            gioHangService.xoaGioHang(maGioHang);
            return ResponseEntity.ok(ApiResponse.success("Xóa giỏ hàng thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
