package com.example.backend.service;


import com.example.backend.dto.DonHangDTO;
import com.example.backend.entity.DonHang;
import com.example.backend.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    public List<DonHangDTO> getDonHangByUser(Integer maND) {

        return donHangRepository.findByMaND(maND)
                .stream()
                .map(dh -> {
                    DonHangDTO dto = new DonHangDTO();
                    dto.setMaDH(dh.getMaDH());
                    dto.setNgayDat(dh.getNgayDat());
                    dto.setTenPhuTung(dh.getTenPhuTung());
                    dto.setHinhAnh(dh.getHinhAnh());
                    dto.setTongTien(dh.getTongTien());
                    dto.setPhiVanChuyen(dh.getPhiVanChuyen());
                    dto.setTongThanhToan(
                            dh.getTongTien().add(dh.getPhiVanChuyen())
                    );
                    dto.setDiaChiGiao(dh.getDiaChiGiao());
                    dto.setTrangThai(dh.getTrangThai());
                    dto.setPhuongThucThanhToan(dh.getPhuongThucThanhToan());
                    return dto;
                }).collect(Collectors.toList());
    }

    /**
     * Tạo đơn hàng mới
     */
    public DonHangDTO createOrder(DonHangDTO dto) {
        DonHang donHang = new DonHang();
        
        // Tạo mã đơn hàng: DH + timestamp
        String maDH = "DH" + System.currentTimeMillis() % 100000000;
        donHang.setMaDH(maDH);
        
        // Set thông tin đơn hàng
        donHang.setNgayDat(LocalDateTime.now());
        donHang.setTenPhuTung(dto.getTenPhuTung() != null ? dto.getTenPhuTung() : "Sản phẩm");
        donHang.setHinhAnh(dto.getHinhAnh() != null ? dto.getHinhAnh() : "default.png");
        donHang.setTongTien(dto.getTongTien());
        donHang.setPhiVanChuyen(dto.getPhiVanChuyen());
        donHang.setDiaChiGiao(dto.getDiaChiGiao() != null ? dto.getDiaChiGiao() : "Chưa có địa chỉ");
        donHang.setMaND(dto.getMaND());
        donHang.setPhuongThucThanhToan(dto.getPhuongThucThanhToan() != null ? dto.getPhuongThucThanhToan() : "Tiền mặt");
        donHang.setTrangThai("Đang giao");
        
        // Ngày nhận dự kiến: 3-5 ngày sau
        donHang.setNgayNhanDuKien(LocalDateTime.now().plusDays(3));
        
        // Lưu vào database
        DonHang saved = donHangRepository.save(donHang);
        
        // Convert to DTO để trả về
        DonHangDTO result = new DonHangDTO();
        result.setMaDH(saved.getMaDH());
        result.setNgayDat(saved.getNgayDat());
        result.setTenPhuTung(saved.getTenPhuTung());
        result.setHinhAnh(saved.getHinhAnh());
        result.setTongTien(saved.getTongTien());
        result.setPhiVanChuyen(saved.getPhiVanChuyen());
        result.setTongThanhToan(saved.getTongTien().add(saved.getPhiVanChuyen()));
        result.setDiaChiGiao(saved.getDiaChiGiao());
        result.setTrangThai(saved.getTrangThai());
        result.setPhuongThucThanhToan(saved.getPhuongThucThanhToan());
        result.setNgayNhanDuKien(saved.getNgayNhanDuKien());
        
        return result;
    }
}
