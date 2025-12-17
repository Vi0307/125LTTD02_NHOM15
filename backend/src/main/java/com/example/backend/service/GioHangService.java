package com.example.backend.service;

import com.example.backend.dto.GioHangDTO;
import com.example.backend.entity.GioHang;
import com.example.backend.repository.GioHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GioHangService {
    
    private final GioHangRepository gioHangRepository;
    
    // Lấy giỏ hàng theo người dùng
    public List<GioHangDTO> getGioHangByNguoiDung(Long maNguoiDung) {
        return gioHangRepository.findByMaNguoiDung(maNguoiDung)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    // Thêm sản phẩm vào giỏ hàng
    @Transactional
    public GioHangDTO themVaoGioHang(GioHangDTO dto) {
        // Kiểm tra sản phẩm đã có trong giỏ chưa
        GioHang existing = gioHangRepository.findByMaNguoiDungAndMaSanPham(
                dto.getMaNguoiDung(), dto.getMaSanPham());
        
        if (existing != null) {
            // Cập nhật số lượng nếu đã có
            existing.setSoLuong(existing.getSoLuong() + dto.getSoLuong());
            existing.setThanhTien(existing.getDonGia().multiply(new BigDecimal(existing.getSoLuong())));
            return toDTO(gioHangRepository.save(existing));
        }
        
        // Thêm mới
        GioHang gioHang = toEntity(dto);
        gioHang.setThanhTien(gioHang.getDonGia().multiply(new BigDecimal(gioHang.getSoLuong())));
        return toDTO(gioHangRepository.save(gioHang));
    }
    
    // Cập nhật số lượng
    @Transactional
    public GioHangDTO capNhatSoLuong(Long maGioHang, Integer soLuong) {
        GioHang gioHang = gioHangRepository.findById(maGioHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        
        gioHang.setSoLuong(soLuong);
        gioHang.setThanhTien(gioHang.getDonGia().multiply(new BigDecimal(soLuong)));
        return toDTO(gioHangRepository.save(gioHang));
    }
    
    // Xóa một sản phẩm khỏi giỏ hàng
    @Transactional
    public void xoaKhoiGioHang(Long maGioHang) {
        gioHangRepository.deleteById(maGioHang);
    }
    
    // Xóa toàn bộ giỏ hàng của người dùng
    @Transactional
    public void xoaToanBoGioHang(Long maNguoiDung) {
        gioHangRepository.deleteByMaNguoiDung(maNguoiDung);
    }
    
    // Đếm số sản phẩm trong giỏ
    public int demSoLuongSanPham(Long maNguoiDung) {
        return gioHangRepository.findByMaNguoiDung(maNguoiDung).size();
    }
    
    // Tính tổng tiền giỏ hàng
    public BigDecimal tinhTongTien(Long maNguoiDung) {
        return gioHangRepository.findByMaNguoiDung(maNguoiDung)
                .stream()
                .map(GioHang::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Convert Entity to DTO
    private GioHangDTO toDTO(GioHang entity) {
        return GioHangDTO.builder()
                .maGioHang(entity.getMaGioHang())
                .maNguoiDung(entity.getMaNguoiDung())
                .maSanPham(entity.getMaSanPham())
                .soLuong(entity.getSoLuong())
                .donGia(entity.getDonGia())
                .thanhTien(entity.getThanhTien())
                .build();
    }
    
    // Convert DTO to Entity
    private GioHang toEntity(GioHangDTO dto) {
        return GioHang.builder()
                .maGioHang(dto.getMaGioHang())
                .maNguoiDung(dto.getMaNguoiDung())
                .maSanPham(dto.getMaSanPham())
                .soLuong(dto.getSoLuong())
                .donGia(dto.getDonGia())
                .thanhTien(dto.getThanhTien())
                .build();
    }
}
