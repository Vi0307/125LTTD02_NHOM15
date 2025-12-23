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

    // Lấy người dùng theo ID
    public NguoiDungDTO getNguoiDungById(Integer maND) {
        return nguoiDungRepository.findById(maND)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với mã: " + maND));
    }

    // Cập nhật số lần bảo dưỡng
    public NguoiDungDTO updateMaintenanceCount(Integer maND, Integer newCount) {
        Optional<NguoiDung> userOpt = nguoiDungRepository.findById(maND);
        if (userOpt.isPresent()) {
            NguoiDung user = userOpt.get();
            user.setSoLanBaoDuong(newCount);
            // Optionally update NgayBaoDuong to now
            user.setNgayBaoDuong(java.time.LocalDateTime.now());
            NguoiDung updatedUser = nguoiDungRepository.save(user);
            return mapToDTO(updatedUser);
        } else {
            throw new RuntimeException("Không tìm thấy người dùng với mã: " + maND);
        }
    }

    // Cập nhật thông tin người dùng
    public NguoiDungDTO updateNguoiDung(Integer maND, NguoiDungDTO dto) {
        Optional<NguoiDung> userOpt = nguoiDungRepository.findById(maND);
        if (userOpt.isPresent()) {
            NguoiDung user = userOpt.get();
            
            // Cập nhật các trường nếu có giá trị mới
            if (dto.getHoTen() != null && !dto.getHoTen().isEmpty()) {
                user.setHoTen(dto.getHoTen());
            }
            if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
                user.setEmail(dto.getEmail());
            }
            if (dto.getDienThoai() != null && !dto.getDienThoai().isEmpty()) {
                user.setDienThoai(dto.getDienThoai());
            }
            if (dto.getNgaySinh() != null) {
                user.setNgaySinh(dto.getNgaySinh());
            }
            
            NguoiDung updatedUser = nguoiDungRepository.save(user);
            return mapToDTO(updatedUser);
        } else {
            throw new RuntimeException("Không tìm thấy người dùng với mã: " + maND);
        }
    }
}
