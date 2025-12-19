package com.example.backend.controller;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.NguoiDungDTO;
import com.example.backend.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nguoidung")
@CrossOrigin(origins = "*")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            NguoiDungDTO user = nguoiDungService.login(loginRequest);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // In a stateless REST API (like this simple implementation), logout is primarily a client-side action (deleting the token/user info).
        // The server can return 200 OK to acknowledge the request.
        return ResponseEntity.ok("Logged out successfully");
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getNguoiDungById(@PathVariable Integer id) {
        try {
            NguoiDungDTO user = nguoiDungService.getNguoiDungById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
