package com.example.backend.service;

import com.example.backend.dto.ChiTietGioHangDTO;
import com.example.backend.dto.ThemVaoGioHangRequest;
import com.example.backend.entity.ChiTietGioHang;
import com.example.backend.entity.GioHang;
import com.example.backend.entity.PhuTung;
import com.example.backend.repository.ChiTietGioHangRepository;
import com.example.backend.repository.GioHangRepository;
import com.example.backend.repository.PhuTungRepository;
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
    private final PhuTungRepository phuTungRepository;
    
    // Lấy tất cả chi tiết trong giỏ hàng
    public List<ChiTietGioHangDTO> getChiTietByGioHang(Integer maGioHang) {
        return chiTietGioHangRepository.findByMaGioHang(maGioHang)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    // Lấy chi tiết giỏ hàng theo người dùng
    public List<ChiTietGioHangDTO> getChiTietByNguoiDung(Integer maND) {
        System.out.println("Getting cart details for user: " + maND);
        List<GioHang> carts = gioHangRepository.findAllByMaND(maND);
        System.out.println("Found " + carts.size() + " carts for user " + maND);
        
        if(carts.isEmpty()) {
             return java.util.Collections.emptyList();
        }
        
        // Merge items from ALL carts
        List<ChiTietGioHangDTO> allItems = new java.util.ArrayList<>();
        for(GioHang gh : carts) {
             List<ChiTietGioHangDTO> items = getChiTietByGioHang(gh.getMaGioHang());
             System.out.println("Cart " + gh.getMaGioHang() + " has " + items.size() + " items");
             allItems.addAll(items);
        }
        System.out.println("Total merged items: " + allItems.size());
        return allItems;
    }
    
    // Thêm sản phẩm vào giỏ hàng
    @Transactional
    public ChiTietGioHangDTO themVaoGioHang(Integer maND, ThemVaoGioHangRequest request) {
        String maPhuTung = request.getMaPhuTung() != null ? request.getMaPhuTung().trim() : "";
        System.out.println("Adding to cart for user: " + maND + ", item: '" + maPhuTung + "'");
        
        // Handle multiple carts: Pick the first one, or create if none
        List<GioHang> carts = gioHangRepository.findAllByMaND(maND);
        GioHang gioHang;
        if (carts.isEmpty()) {
            System.out.println("No cart found, creating new one.");
            gioHang = gioHangRepository.save(GioHang.builder().maND(maND).build());
        } else {
            gioHang = carts.get(0);
             System.out.println("Using existing cart ID: " + gioHang.getMaGioHang());
        }
        
        // Kiểm tra phụ tùng đã có trong giỏ chưa
        var existingItem = chiTietGioHangRepository
                .findByMaGioHangAndMaPhuTung(gioHang.getMaGioHang(), maPhuTung);
        
        if (existingItem.isPresent()) {
            // Cập nhật số lượng nếu đã có
            ChiTietGioHang item = existingItem.get();
            item.setSoLuong(item.getSoLuong() + request.getSoLuong());
            System.out.println("Updated quantity for item: " + item.getMaCTGH());
            return toDTO(chiTietGioHangRepository.save(item));
        } else {
            // Thêm mới
            BigDecimal donGia = request.getDonGia() != null ? request.getDonGia() : BigDecimal.ZERO;
            
            ChiTietGioHang newItem = ChiTietGioHang.builder()
                    .maGioHang(gioHang.getMaGioHang())
                    .maPhuTung(maPhuTung)
                    .hinhAnh(request.getHinhAnh())
                    .soLuong(request.getSoLuong() != null ? request.getSoLuong() : 1)
                    .donGia(donGia)
                    .build();
            System.out.println("Created new item: " + newItem);
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
        List<GioHang> carts = gioHangRepository.findAllByMaND(maND);
        if(carts.isEmpty()) throw new RuntimeException("Người dùng chưa có giỏ hàng");
        
        for(GioHang gh : carts) {
            chiTietGioHangRepository.deleteByMaGioHang(gh.getMaGioHang());
        }
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
        List<GioHang> carts = gioHangRepository.findAllByMaND(maND);
        if(carts.isEmpty()) throw new RuntimeException("Người dùng chưa có giỏ hàng");
        
        BigDecimal total = BigDecimal.ZERO;
        for(GioHang gh : carts) {
            total = total.add(tinhTongTien(gh.getMaGioHang()));
        }
        return total;
    }
    
    // Convert Entity to DTO
    private ChiTietGioHangDTO toDTO(ChiTietGioHang entity) {
        // Lấy tên phụ tùng từ database
        // Handle potential nulls and trimming
        String tenPhuTung = "Unknown Product";
        try {
             tenPhuTung = phuTungRepository.findById(entity.getMaPhuTung())
                .map(PhuTung::getTenPhuTung)
                .orElse("Sản phẩm không còn tồn tại");
        } catch (Exception e) {
            System.err.println("Error fecthing product name for ID: " + entity.getMaPhuTung() + " - " + e.getMessage());
        }

        
        return ChiTietGioHangDTO.builder()
                .maCTGH(entity.getMaCTGH())
                .maGioHang(entity.getMaGioHang())
                .maPhuTung(entity.getMaPhuTung())
                .tenPhuTung(tenPhuTung)
                .hinhAnh(entity.getHinhAnh())
                .soLuong(entity.getSoLuong())
                .donGia(entity.getDonGia())
                .build();
    }
}
