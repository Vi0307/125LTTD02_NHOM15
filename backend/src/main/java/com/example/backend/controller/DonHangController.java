package com.example.backend.controller;

import com.example.backend.dto.DonHangDTO;
import com.example.backend.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @GetMapping("/{maND}")
    public List<DonHangDTO> getOrders(@PathVariable Integer maND) {
        return donHangService.getDonHangByUser(maND);
    }

    /**
     * Lấy đơn hàng đang giao theo user
     * GET /api/orders/{maND}/delivering
     */
    @GetMapping("/{maND}/delivering")
    public List<DonHangDTO> getDeliveringOrders(@PathVariable Integer maND) {
        return donHangService.getDonHangDangGiao(maND);
    }

    /**
     * Lấy đơn hàng đã giao theo user
     * GET /api/orders/{maND}/delivered
     */
    @GetMapping("/{maND}/delivered")
    public List<DonHangDTO> getDeliveredOrders(@PathVariable Integer maND) {
        return donHangService.getDonHangDaGiao(maND);
    }

    /**
     * Lấy chi tiết đơn hàng theo mã đơn hàng
     * GET /api/orders/info/{maDH}
     */
    @GetMapping("/info/{maDH}")
    public DonHangDTO getOrderById(@PathVariable String maDH) {
        return donHangService.getDonHangById(maDH);
    }

    /**
     * Tạo đơn hàng mới
     * POST /api/orders
     */
    @PostMapping
    public DonHangDTO createOrder(@RequestBody DonHangDTO donHangDTO) {
        return donHangService.createOrder(donHangDTO);
    }
}

