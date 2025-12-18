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

    @Transactional
    public XeDTO createXe(XeDTO dto) {
        if (xeRepository.existsById(dto.getMaXe())) {
            throw new RuntimeException("Vehicle ID already exists: " + dto.getMaXe());
        }
        Xe xe = toEntity(dto);
        return toDTO(xeRepository.save(xe));
    }

    @Transactional
    public XeDTO updateXe(String id, XeDTO dto) {
        Xe xe = xeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));
        
        xe.setMaHangXe(dto.getMaHangXe());
        xe.setMaND(dto.getMaND());
        xe.setMaLoaiXe(dto.getMaLoaiXe());
        xe.setBienSo(dto.getBienSo());
        xe.setDungTich(dto.getDungTich());
        xe.setSoKhung(dto.getSoKhung());
        xe.setMauSac(dto.getMauSac());
        xe.setHinhAnh(dto.getHinhAnh());
        
        return toDTO(xeRepository.save(xe));
    }

    @Transactional
    public void deleteXe(String id) {
        if (!xeRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id " + id);
        }
        xeRepository.deleteById(id);
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
