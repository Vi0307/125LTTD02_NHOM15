package com.example.backend.service;


import com.example.backend.dto.DonHangDTO;
import com.example.backend.dto.PhuongThucThanhToanDTO;
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

    @Autowired
    private PhuongThucThanhToanService phuongThucThanhToanService;

    public List<DonHangDTO> getDonHangByUser(Integer maND) {

        return donHangRepository.findByMaND(maND)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách đơn hàng đang giao theo user
     */
    public List<DonHangDTO> getDonHangDangGiao(Integer maND) {
        return donHangRepository.findByMaNDAndTrangThai(maND, "Đang giao")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lấy danh sách đơn hàng đã giao theo user
     */
    public List<DonHangDTO> getDonHangDaGiao(Integer maND) {
        return donHangRepository.findByMaNDAndTrangThai(maND, "Đã giao")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lấy chi tiết đơn hàng theo mã đơn hàng
     */
    public DonHangDTO getDonHangById(String maDH) {
        return donHangRepository.findById(maDH)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * Convert entity to DTO
     */
    private DonHangDTO convertToDTO(DonHang dh) {
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
        dto.setMaPTTT(dh.getMaPTTT());
        dto.setNgayNhanDuKien(dh.getNgayNhanDuKien());
        // Lấy tên phương thức thanh toán từ service
        if (dh.getMaPTTT() != null) {
            PhuongThucThanhToanDTO pttt = phuongThucThanhToanService.getPaymentMethodById(dh.getMaPTTT());
            dto.setPhuongThucThanhToan(pttt != null ? pttt.getTenPTTT() : "Tiền mặt");
        } else {
            dto.setPhuongThucThanhToan("Tiền mặt");
        }
        return dto;
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
        // Set mã voucher và phương thức vận chuyển
        donHang.setMaVC(dto.getMaVC());
        donHang.setMaPTVC(dto.getMaPTVC());
        // Sử dụng maPTTT thay vì phuongThucThanhToan
        donHang.setMaPTTT(dto.getMaPTTT() != null ? dto.getMaPTTT() : 1); // Mặc định là Tiền mặt (ID=1)
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
        result.setMaPTTT(saved.getMaPTTT());
        // Lấy tên phương thức thanh toán
        if (saved.getMaPTTT() != null) {
            PhuongThucThanhToanDTO pttt = phuongThucThanhToanService.getPaymentMethodById(saved.getMaPTTT());
            result.setPhuongThucThanhToan(pttt != null ? pttt.getTenPTTT() : "Tiền mặt");
        } else {
            result.setPhuongThucThanhToan("Tiền mặt");
        }
        result.setNgayNhanDuKien(saved.getNgayNhanDuKien());
        
        return result;
    }
}
