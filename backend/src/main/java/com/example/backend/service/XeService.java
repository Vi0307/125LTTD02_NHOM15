package com.example.backend.service;

import com.example.backend.dto.XeDTO;
import com.example.backend.entity.Xe;
import com.example.backend.repository.XeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class XeService {

    private final XeRepository xeRepository;

    public List<XeDTO> getAllXe() {
        return xeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public XeDTO getXeById(String id) {
        Xe xe = xeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));
        return toDTO(xe);
    }

    public List<XeDTO> getXeByNguoiDung(Integer maND) {
        return xeRepository.findByMaND(maND).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private XeDTO toDTO(Xe entity) {
        return XeDTO.builder()
                .maXe(entity.getMaXe())
                .maHangXe(entity.getMaHangXe())
                .maND(entity.getMaND())
                .maLoaiXe(entity.getMaLoaiXe())
                .bienSo(entity.getBienSo())
                .dungTich(entity.getDungTich())
                .soKhung(entity.getSoKhung())
                .mauSac(entity.getMauSac())
                .hinhAnh(entity.getHinhAnh())
                .build();
    }

    private Xe toEntity(XeDTO dto) {
        return Xe.builder()
                .maXe(dto.getMaXe())
                .maHangXe(dto.getMaHangXe())
                .maND(dto.getMaND())
                .maLoaiXe(dto.getMaLoaiXe())
                .bienSo(dto.getBienSo())
                .dungTich(dto.getDungTich())
                .soKhung(dto.getSoKhung())
                .mauSac(dto.getMauSac())
                .hinhAnh(dto.getHinhAnh())
                .build();
    }
}
