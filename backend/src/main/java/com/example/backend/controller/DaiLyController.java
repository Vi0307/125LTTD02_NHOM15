package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.DaiLyDTO;
import com.example.backend.service.DaiLyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DaiLyController {

    private final DaiLyService daiLyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DaiLyDTO>>> getAllDaiLy() {
        return ResponseEntity.ok(ApiResponse.success(daiLyService.getAllDaiLy()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DaiLyDTO>> getDaiLyById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ApiResponse.success(daiLyService.getDaiLyById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
