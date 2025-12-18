package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ChiTietGioHangDTO;
import com.example.backend.dto.ThemVaoGioHangRequest;
import com.example.backend.service.ChiTietGioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/chi-tiet-gio-hang")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChiTietGioHangController {
    
    private final ChiTietGioHangService chiTietGioHangService;
    
    // Lấy chi tiết giỏ hàng theo mã giỏ hàng
    @GetMapping("/gio-hang/{maGioHang}")
    public ResponseEntity<ApiResponse<List<ChiTietGioHangDTO>>> getChiTietByGioHang(
            @PathVariable Integer maGioHang) {
        try {
            List<ChiTietGioHangDTO> list = chiTietGioHangService.getChiTietByGioHang(maGioHang);
            return ResponseEntity.ok(ApiResponse.success(list));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Lấy chi tiết giỏ hàng theo người dùng
    @GetMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<List<ChiTietGioHangDTO>>> getChiTietByNguoiDung(
            @PathVariable Integer maND) {
        try {
            List<ChiTietGioHangDTO> list = chiTietGioHangService.getChiTietByNguoiDung(maND);
            return ResponseEntity.ok(ApiResponse.success(list));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<ChiTietGioHangDTO>> themVaoGioHang(
            @PathVariable Integer maND,
            @RequestBody ThemVaoGioHangRequest request) {
        try {
            ChiTietGioHangDTO dto = chiTietGioHangService.themVaoGioHang(maND, request);
            return ResponseEntity.ok(ApiResponse.success("Thêm vào giỏ hàng thành công", dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Cập nhật số lượng sản phẩm
    @PutMapping("/{maCTGH}/so-luong")
    public ResponseEntity<ApiResponse<ChiTietGioHangDTO>> capNhatSoLuong(
            @PathVariable Integer maCTGH,
            @RequestParam Integer soLuong) {
        try {
            ChiTietGioHangDTO dto = chiTietGioHangService.capNhatSoLuong(maCTGH, soLuong);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật số lượng thành công", dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{maCTGH}")
    public ResponseEntity<ApiResponse<Void>> xoaKhoiGioHang(
            @PathVariable Integer maCTGH) {
        try {
            chiTietGioHangService.xoaKhoiGioHang(maCTGH);
            return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Xóa tất cả sản phẩm trong giỏ hàng
    @DeleteMapping("/gio-hang/{maGioHang}/clear")
    public ResponseEntity<ApiResponse<Void>> xoaTatCa(
            @PathVariable Integer maGioHang) {
        try {
            chiTietGioHangService.xoaTatCa(maGioHang);
            return ResponseEntity.ok(ApiResponse.success("Đã xóa tất cả sản phẩm trong giỏ hàng"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Xóa tất cả sản phẩm theo người dùng
    @DeleteMapping("/nguoi-dung/{maND}/clear")
    public ResponseEntity<ApiResponse<Void>> xoaTatCaByNguoiDung(
            @PathVariable Integer maND) {
        try {
            chiTietGioHangService.xoaTatCaByNguoiDung(maND);
            return ResponseEntity.ok(ApiResponse.success("Đã xóa tất cả sản phẩm trong giỏ hàng"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Đếm số sản phẩm trong giỏ hàng
    @GetMapping("/gio-hang/{maGioHang}/count")
    public ResponseEntity<ApiResponse<Long>> demSoSanPham(
            @PathVariable Integer maGioHang) {
        try {
            long count = chiTietGioHangService.demSoSanPham(maGioHang);
            return ResponseEntity.ok(ApiResponse.success(count));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Tính tổng tiền giỏ hàng
    @GetMapping("/gio-hang/{maGioHang}/tong-tien")
    public ResponseEntity<ApiResponse<BigDecimal>> tinhTongTien(
            @PathVariable Integer maGioHang) {
        try {
            BigDecimal tongTien = chiTietGioHangService.tinhTongTien(maGioHang);
            return ResponseEntity.ok(ApiResponse.success(tongTien));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    // Tính tổng tiền giỏ hàng theo người dùng
    @GetMapping("/nguoi-dung/{maND}/tong-tien")
    public ResponseEntity<ApiResponse<BigDecimal>> tinhTongTienByNguoiDung(
            @PathVariable Integer maND) {
        try {
            BigDecimal tongTien = chiTietGioHangService.tinhTongTienByNguoiDung(maND);
            return ResponseEntity.ok(ApiResponse.success(tongTien));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
