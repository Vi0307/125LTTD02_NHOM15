package com.example.backend.service;

import com.example.backend.dto.PhuongThucThanhToanDTO;
import com.example.backend.entity.PhuongThucThanhToan;
import com.example.backend.repository.PhuongThucThanhToanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Payment Methods
 * Lấy dữ liệu từ bảng PHUONG_THUC_THANH_TOAN trong database
 */
@Service
public class PhuongThucThanhToanService {

    @Autowired
    private PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    /**
     * Chuyển đổi Entity sang DTO
     */
    private PhuongThucThanhToanDTO convertToDTO(PhuongThucThanhToan entity, boolean isDefault) {
        return new PhuongThucThanhToanDTO(
            entity.getMaPTTT(),
            entity.getTenPTTT(),
            entity.getMoTa(),
            entity.getIcon(),
            isDefault
        );
    }

    /**
     * Lấy tất cả phương thức thanh toán đang hoạt động từ database
     */
    public List<PhuongThucThanhToanDTO> getAllPaymentMethods() {
        List<PhuongThucThanhToan> entities = phuongThucThanhToanRepository.findByTrangThaiTrue();
        
        // Đánh dấu phương thức đầu tiên là mặc định
        return entities.stream()
                .map(entity -> {
                    boolean isDefault = entities.indexOf(entity) == 0;
                    return convertToDTO(entity, isDefault);
                })
                .collect(Collectors.toList());
    }

    /**
     * Lấy phương thức thanh toán theo ID
     */
    public PhuongThucThanhToanDTO getPaymentMethodById(Integer id) {
        return phuongThucThanhToanRepository.findById(id)
                .map(entity -> convertToDTO(entity, false))
                .orElse(null);
    }

    /**
     * Lấy phương thức thanh toán mặc định (phương thức đầu tiên đang hoạt động)
     */
    public PhuongThucThanhToanDTO getDefaultPaymentMethod() {
        PhuongThucThanhToan entity = phuongThucThanhToanRepository.findFirstByTrangThaiTrueOrderByMaPTTTAsc();
        return entity != null ? convertToDTO(entity, true) : null;
    }
}
