package com.example.backend.service;

import com.example.backend.dto.DichVuDTO;
import com.example.backend.entity.DichVu;
import com.example.backend.repository.DichVuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DichVuService {

    private final DichVuRepository dichVuRepository;

    public List<DichVuDTO> getAllDichVu() {
        return dichVuRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DichVuDTO getDichVuById(Integer id) {
        DichVu dichVu = dichVuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id " + id));
        return toDTO(dichVu);
    }

    public List<DichVuDTO> getDichVuByNguoiDung(Integer maND) {
        return dichVuRepository.findByMaND(maND).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<DichVuDTO> getDichVuByDaiLy(Integer maDaiLy) {
        return dichVuRepository.findByMaDaiLy(maDaiLy).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DichVuDTO createDichVu(DichVuDTO dto) {
        DichVu dichVu = toEntity(dto);
        dichVu.setMaDV(null); // Ensure ID is generated
        
        // Default values if missing (handled in entity @PrePersist too, but safe here)
        if (dichVu.getTrangThai() == null) {
            dichVu.setTrangThai("Chưa hoàn thành");
        }
        
        return toDTO(dichVuRepository.save(dichVu));
    }
    private DichVuDTO toDTO(DichVu entity) {
        return DichVuDTO.builder()
                .maDV(entity.getMaDV())
                .maND(entity.getMaND())
                .loaiDichVu(entity.getLoaiDichVu())
                .moTa(entity.getMoTa())
                .trangThai(entity.getTrangThai())
                .ngayTao(entity.getNgayTao())
                .maDaiLy(entity.getMaDaiLy())
                .build();
    }

    private DichVu toEntity(DichVuDTO dto) {
        return DichVu.builder()
                .maDV(dto.getMaDV())
                .maND(dto.getMaND())
                .loaiDichVu(dto.getLoaiDichVu())
                .moTa(dto.getMoTa())
                .trangThai(dto.getTrangThai())
                .ngayTao(dto.getNgayTao())
                .maDaiLy(dto.getMaDaiLy())
                .build();
    }
}
