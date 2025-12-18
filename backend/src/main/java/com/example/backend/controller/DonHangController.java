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
}

