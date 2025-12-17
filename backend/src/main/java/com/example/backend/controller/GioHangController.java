package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.GioHangDTO;
import com.example.backend.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gio-hang")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GioHangController {
    
    private final GioHangService gioHangService;
    
    // Lấy giỏ hàng theo người dùng
    @GetMapping("/nguoi-dung/{maNguoiDung}")
    public ResponseEntity<ApiResponse<List<GioHangDTO>>> getGioHang(
            @PathVariable Long maNguoiDung) {
        List<GioHangDTO> gioHang = gioHangService.getGioHangByNguoiDung(maNguoiDung);
        return ResponseEntity.ok(ApiResponse.success("Lấy giỏ hàng thành công", gioHang));
    }
    
    // Thêm sản phẩm vào giỏ hàng
    @PostMapping
    public ResponseEntity<ApiResponse<GioHangDTO>> themVaoGioHang(
            @RequestBody GioHangDTO dto) {
        try {
            GioHangDTO result = gioHangService.themVaoGioHang(dto);
            return ResponseEntity.ok(ApiResponse.success("Thêm vào giỏ hàng thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Không thể thêm vào giỏ hàng", e.getMessage()));
        }
    }
    
    // Cập nhật số lượng
    @PutMapping("/{maGioHang}")
    public ResponseEntity<ApiResponse<GioHangDTO>> capNhatSoLuong(
            @PathVariable Long maGioHang,
            @RequestBody Map<String, Integer> request) {
        try {
            Integer soLuong = request.get("soLuong");
            GioHangDTO result = gioHangService.capNhatSoLuong(maGioHang, soLuong);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật số lượng thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Không thể cập nhật số lượng", e.getMessage()));
        }
    }
    
    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{maGioHang}")
    public ResponseEntity<ApiResponse<Void>> xoaKhoiGioHang(
            @PathVariable Long maGioHang) {
        try {
            gioHangService.xoaKhoiGioHang(maGioHang);
            return ResponseEntity.ok(ApiResponse.success("Xóa khỏi giỏ hàng thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Không thể xóa khỏi giỏ hàng", e.getMessage()));
        }
    }
    
    // Xóa toàn bộ giỏ hàng
    @DeleteMapping("/nguoi-dung/{maNguoiDung}")
    public ResponseEntity<ApiResponse<Void>> xoaToanBoGioHang(
            @PathVariable Long maNguoiDung) {
        try {
            gioHangService.xoaToanBoGioHang(maNguoiDung);
            return ResponseEntity.ok(ApiResponse.success("Xóa toàn bộ giỏ hàng thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Không thể xóa giỏ hàng", e.getMessage()));
        }
    }
    
    // Đếm số lượng sản phẩm trong giỏ
    @GetMapping("/nguoi-dung/{maNguoiDung}/so-luong")
    public ResponseEntity<ApiResponse<Integer>> demSoLuong(
            @PathVariable Long maNguoiDung) {
        int soLuong = gioHangService.demSoLuongSanPham(maNguoiDung);
        return ResponseEntity.ok(ApiResponse.success(soLuong));
    }
    
    // Tính tổng tiền giỏ hàng
    @GetMapping("/nguoi-dung/{maNguoiDung}/tong-tien")
    public ResponseEntity<ApiResponse<BigDecimal>> tinhTongTien(
            @PathVariable Long maNguoiDung) {
        BigDecimal tongTien = gioHangService.tinhTongTien(maNguoiDung);
        return ResponseEntity.ok(ApiResponse.success(tongTien));
    }
}
