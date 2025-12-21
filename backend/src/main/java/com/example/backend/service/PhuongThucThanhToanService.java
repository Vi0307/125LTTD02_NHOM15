package com.example.backend.service;

import com.example.backend.dto.PhuongThucThanhToanDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for Payment Methods
 * Trả về danh sách các phương thức thanh toán tĩnh (không lưu trong database)
 */
@Service
public class PhuongThucThanhToanService {

    /**
     * Lấy tất cả phương thức thanh toán
     * Các phương thức này tương ứng với CHECK constraint trong bảng DON_HANG:
     * 'Tiền mặt', 'Apple Pay', 'Ngân hàng liên kết'
     */
    public List<PhuongThucThanhToanDTO> getAllPaymentMethods() {
        List<PhuongThucThanhToanDTO> methods = new ArrayList<>();

        methods.add(new PhuongThucThanhToanDTO(
            1,
            "Tiền mặt",
            "Thanh toán bằng tiền mặt khi nhận hàng",
            "today_24dp_icon",
            true
        ));

        methods.add(new PhuongThucThanhToanDTO(
            2,
            "Apple Pay",
            "Thanh toán qua Apple Pay",
            "ic_apple",
            false
        ));

        methods.add(new PhuongThucThanhToanDTO(
            3,
            "Ngân hàng liên kết",
            "Thanh toán qua ngân hàng liên kết",
            "ic_bank_payment",
            false
        ));

        return methods;
    }

    /**
     * Lấy phương thức thanh toán theo ID
     */
    public PhuongThucThanhToanDTO getPaymentMethodById(Integer id) {
        List<PhuongThucThanhToanDTO> methods = getAllPaymentMethods();
        return methods.stream()
                .filter(m -> m.getMaPTTT().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lấy phương thức thanh toán mặc định
     */
    public PhuongThucThanhToanDTO getDefaultPaymentMethod() {
        List<PhuongThucThanhToanDTO> methods = getAllPaymentMethods();
        return methods.stream()
                .filter(m -> Boolean.TRUE.equals(m.getMacDinh()))
                .findFirst()
                .orElse(methods.isEmpty() ? null : methods.get(0));
    }
}
