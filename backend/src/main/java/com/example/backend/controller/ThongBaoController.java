package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.ThongBaoDTO;
import com.example.backend.service.ThongBaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-bao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ThongBaoController {

    private final ThongBaoService thongBaoService;

    @GetMapping("/nguoi-dung/{maND}")
    public ResponseEntity<ApiResponse<List<ThongBaoDTO>>> getThongBaoByMaND(@PathVariable Integer maND) {
        return ResponseEntity.ok(ApiResponse.success(thongBaoService.getThongBaoByMaND(maND)));
    }
}
