package com.example.backend.service;

import com.example.backend.dto.GioHangDTO;
import com.example.backend.entity.GioHang;
import com.example.backend.repository.GioHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GioHangService {
    
    private final GioHangRepository gioHangRepository;
    
    // Lấy tất cả giỏ hàng
    public List<GioHangDTO> getAllGioHang() {
        return gioHangRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy giỏ hàng theo ID
    public GioHangDTO getGioHangById(Integer maGioHang) {
        GioHang gioHang = gioHangRepository.findById(maGioHang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        return toDTO(gioHang);
    }
    
    // Lấy giỏ hàng theo người dùng
    public GioHangDTO getGioHangByNguoiDung(Integer maND) {
        GioHang gioHang = gioHangRepository.findByMaND(maND)
                .orElseThrow(() -> new RuntimeException("Người dùng chưa có giỏ hàng"));
        return toDTO(gioHang);
    }
    
    // Tạo giỏ hàng mới cho người dùng
    @Transactional
    public GioHangDTO taoGioHang(Integer maND) {
        // Kiểm tra đã có giỏ hàng chưa
        if (gioHangRepository.existsByMaND(maND)) {
            throw new RuntimeException("Người dùng đã có giỏ hàng");
        }
        
        GioHang gioHang = GioHang.builder()
                .maND(maND)
                .build();
        return toDTO(gioHangRepository.save(gioHang));
    }
    
    // Lấy hoặc tạo giỏ hàng
    @Transactional
    public GioHangDTO getOrCreateGioHang(Integer maND) {
        return gioHangRepository.findByMaND(maND)
                .map(this::toDTO)
                .orElseGet(() -> taoGioHang(maND));
    }
    
    // Xóa giỏ hàng
    @Transactional
    public void xoaGioHang(Integer maGioHang) {
        gioHangRepository.deleteById(maGioHang);
    }
    
    // Convert Entity to DTO
    private GioHangDTO toDTO(GioHang entity) {
        return GioHangDTO.builder()
                .maGioHang(entity.getMaGioHang())
                .maND(entity.getMaND())
                .build();
    }
}
