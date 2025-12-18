package com.example.backend.service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.NguoiDungDTO;
import com.example.backend.entity.NguoiDung;
import com.example.backend.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public NguoiDungDTO login(LoginRequest request) {
        // Try to find by email or phone
        Optional<NguoiDung> userOpt = nguoiDungRepository.findByEmailOrDienThoai(request.getIdentifier(), request.getIdentifier());
        
        if (userOpt.isPresent()) {
            NguoiDung user = userOpt.get();
            // Simple password check (plaintext as per SQL sample context, usually should be BCrypt)
            if (user.getMatKhau().equals(request.getPassword())) {
                if (user.getIsLocked()) {
                    throw new RuntimeException("Account is locked");
                }
                return mapToDTO(user);
            }
        }
        return null;
    }

    private NguoiDungDTO mapToDTO(NguoiDung user) {
        return NguoiDungDTO.builder()
                .maND(user.getMaND())
                .hoTen(user.getHoTen())
                .email(user.getEmail())
                .dienThoai(user.getDienThoai())
                .ngaySinh(user.getNgaySinh())
                .vaiTro(user.getVaiTro())
                .ngayBaoDuong(user.getNgayBaoDuong())
                .soLanBaoDuong(user.getSoLanBaoDuong())
                .isLocked(user.getIsLocked())
                .build();
    }
}
