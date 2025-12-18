package com.example.backend.controller;

import com.example.backend.dto.VoucherDTO;
import com.example.backend.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    @Autowired
    private VoucherService service;

    @GetMapping("/{maND}")
    public List<VoucherDTO> getVoucher(@PathVariable Integer maND) {
        return service.getVoucherByUser(maND);
    }
}

