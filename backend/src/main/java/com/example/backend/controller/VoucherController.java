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

    @GetMapping
    public List<VoucherDTO> getAllVouchers() {
        return service.getAllVouchers();
    }

    @org.springframework.web.bind.annotation.PostMapping("/reward")
    public org.springframework.http.ResponseEntity<com.example.backend.dto.ApiResponse<String>> rewardVoucher(@org.springframework.web.bind.annotation.RequestBody com.example.backend.dto.RewardRequest request) {
        System.out.println("Reward Request Received: User=" + request.getMaND() + ", Score=" + request.getCorrectAnswers());
        service.rewardVoucher(request.getMaND(), request.getCorrectAnswers());
        return org.springframework.http.ResponseEntity.ok(com.example.backend.dto.ApiResponse.success("", "Đã ghi nhận kết quả"));
    }
}

