package com.example.backend.controller;

import com.example.backend.dto.PhuongThucThanhToanDTO;
import com.example.backend.service.PhuongThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Payment Methods API
 */
@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin
public class PhuongThucThanhToanController {

    @Autowired
    private PhuongThucThanhToanService phuongThucThanhToanService;

    /**
     * Lấy tất cả phương thức thanh toán
     * GET /api/payment-methods
     */
    @GetMapping
    public List<PhuongThucThanhToanDTO> getAllPaymentMethods() {
        return phuongThucThanhToanService.getAllPaymentMethods();
    }

    /**
     * Lấy phương thức thanh toán theo ID
     * GET /api/payment-methods/{id}
     */
    @GetMapping("/{id}")
    public PhuongThucThanhToanDTO getPaymentMethodById(@PathVariable Integer id) {
        return phuongThucThanhToanService.getPaymentMethodById(id);
    }

    /**
     * Lấy phương thức thanh toán mặc định
     * GET /api/payment-methods/default
     */
    @GetMapping("/default")
    public PhuongThucThanhToanDTO getDefaultPaymentMethod() {
        return phuongThucThanhToanService.getDefaultPaymentMethod();
    }
}
