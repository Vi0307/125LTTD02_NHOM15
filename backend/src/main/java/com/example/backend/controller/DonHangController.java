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

    /**
     * Lấy chi tiết đơn hàng theo mã đơn hàng
     * GET /api/orders/info/{maDH}
     * Note: Using /info instead of /detail to avoid conflict with ChiTietDonHangController
     */
    @GetMapping("/info/{maDH}")
    public DonHangDTO getOrderById(@PathVariable String maDH) {
        return donHangService.getDonHangById(maDH);
    }

    /**
     * Lấy tất cả đơn hàng theo user
     * GET /api/orders/user/{maND}
     */
    @GetMapping("/user/{maND}")
    public List<DonHangDTO> getOrders(@PathVariable Integer maND) {
        return donHangService.getDonHangByUser(maND);
    }

    /**
     * Lấy đơn hàng đang giao theo user
     * GET /api/orders/user/{maND}/delivering
     */
    @GetMapping("/user/{maND}/delivering")
    public List<DonHangDTO> getDeliveringOrders(@PathVariable Integer maND) {
        return donHangService.getDonHangDangGiao(maND);
    }

    /**
     * Lấy đơn hàng đã giao theo user
     * GET /api/orders/user/{maND}/delivered
     */
    @GetMapping("/user/{maND}/delivered")
    public List<DonHangDTO> getDeliveredOrders(@PathVariable Integer maND) {
        return donHangService.getDonHangDaGiao(maND);
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
