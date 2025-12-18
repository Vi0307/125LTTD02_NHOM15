package com.example.backend.controller;

import com.example.backend.dto.ChiTietDonHangDTO;
import com.example.backend.service.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/detail")
@CrossOrigin
public class ChiTietDonHangController {

    @Autowired
    private ChiTietDonHangService service;

    @GetMapping("/{maDH}")
    public List<ChiTietDonHangDTO> getOrderDetail(@PathVariable String maDH) {
        return service.getChiTietByMaDH(maDH);
    }
}
