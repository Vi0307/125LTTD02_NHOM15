package com.example.backend.service;

import com.example.backend.dto.ChiTietGioHangDTO;
import com.example.backend.dto.ThemVaoGioHangRequest;
import com.example.backend.entity.ChiTietGioHang;
import com.example.backend.entity.GioHang;
import com.example.backend.repository.ChiTietGioHangRepository;
import com.example.backend.repository.GioHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietGioHangService {
    
    private final ChiTietGioHangRepository chiTietGioHangRepository;
    private final GioHangRepository gioHangRepository;
    
    // Lấy tất cả chi tiết trong giỏ hàng
    public List<ChiTietGioHangDTO> getChiTietByGioHang(Integer maGioHang) {
        return chiTietGioHangRepository.findByMaGioHang(maGioHang)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy chi tiết giỏ hàng theo người dùng
    public List<ChiTietGioHangDTO> getChiTietByNguoiDung(Integer maND) {
        GioHang gioHang = gioHangRepository.findByMaND(maND)
                .orElseThrow(() -> new RuntimeException("Người dùng chưa có giỏ hàng"));
        return getChiTietByGioHang(gioHang.getMaGioHang());
    }
    
    // Thêm sản phẩm vào giỏ hàng
    @Transactional
    public ChiTietGioHangDTO themVaoGioHang(Integer maND, ThemVaoGioHangRequest request) {
        // Lấy hoặc tạo giỏ hàng
        GioHang gioHang = gioHangRepository.findByMaND(maND)
                .orElseGet(() -> gioHangRepository.save(GioHang.builder().maND(maND).build()));
        
        // Kiểm tra phụ tùng đã có trong giỏ chưa
        var existingItem = chiTietGioHangRepository
                .findByMaGioHangAndMaPhuTung(gioHang.getMaGioHang(), request.getMaPhuTung());
        
        if (existingItem.isPresent()) {
            // Cập nhật số lượng nếu đã có
            ChiTietGioHang item = existingItem.get();
            item.setSoLuong(item.getSoLuong() + request.getSoLuong());
            return toDTO(chiTietGioHangRepository.save(item));
        } else {
            // Thêm mới
            ChiTietGioHang newItem = ChiTietGioHang.builder()
                    .maGioHang(gioHang.getMaGioHang())
                    .maPhuTung(request.getMaPhuTung())
                    .hinhAnh(request.getHinhAnh())
                    .soLuong(request.getSoLuong() != null ? request.getSoLuong() : 1)
                    .donGia(request.getDonGia())
                    .build();
            return toDTO(chiTietGioHangRepository.save(newItem));
        }
    }
    
    // Cập nhật số lượng
    @Transactional
    public ChiTietGioHangDTO capNhatSoLuong(Integer maCTGH, Integer soLuong) {
        if (soLuong <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }
        
        ChiTietGioHang item = chiTietGioHangRepository.findById(maCTGH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));
        
        item.setSoLuong(soLuong);
        return toDTO(chiTietGioHangRepository.save(item));
    }
    
    // Xóa sản phẩm khỏi giỏ hàng
    @Transactional
    public void xoaKhoiGioHang(Integer maCTGH) {
        if (!chiTietGioHangRepository.existsById(maCTGH)) {
            throw new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
        chiTietGioHangRepository.deleteById(maCTGH);
    }
    
    // Xóa tất cả sản phẩm trong giỏ hàng
    @Transactional
    public void xoaTatCa(Integer maGioHang) {
        chiTietGioHangRepository.deleteByMaGioHang(maGioHang);
    }
    
    // Xóa tất cả sản phẩm trong giỏ hàng theo người dùng
    @Transactional
    public void xoaTatCaByNguoiDung(Integer maND) {
        GioHang gioHang = gioHangRepository.findByMaND(maND)
                .orElseThrow(() -> new RuntimeException("Người dùng chưa có giỏ hàng"));
        chiTietGioHangRepository.deleteByMaGioHang(gioHang.getMaGioHang());
    }
    
    // Đếm số sản phẩm trong giỏ hàng
    public long demSoSanPham(Integer maGioHang) {
        return chiTietGioHangRepository.countByMaGioHang(maGioHang);
    }
    
    // Tính tổng tiền giỏ hàng
    public BigDecimal tinhTongTien(Integer maGioHang) {
        return chiTietGioHangRepository.findByMaGioHang(maGioHang)
                .stream()
                .map(item -> item.getDonGia().multiply(BigDecimal.valueOf(item.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Tính tổng tiền giỏ hàng theo người dùng
    public BigDecimal tinhTongTienByNguoiDung(Integer maND) {
        GioHang gioHang = gioHangRepository.findByMaND(maND)
                .orElseThrow(() -> new RuntimeException("Người dùng chưa có giỏ hàng"));
        return tinhTongTien(gioHang.getMaGioHang());
    }
    
    // Convert Entity to DTO
    private ChiTietGioHangDTO toDTO(ChiTietGioHang entity) {
        return ChiTietGioHangDTO.builder()
                .maCTGH(entity.getMaCTGH())
                .maGioHang(entity.getMaGioHang())
                .maPhuTung(entity.getMaPhuTung())
                .hinhAnh(entity.getHinhAnh())
                .soLuong(entity.getSoLuong())
                .donGia(entity.getDonGia())
                .build();
    }
}
