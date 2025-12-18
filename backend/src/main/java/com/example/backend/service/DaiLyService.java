package com.example.backend.service;

import com.example.backend.dto.DaiLyDTO;
import com.example.backend.entity.DaiLy;
import com.example.backend.repository.DaiLyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DaiLyService {

    private final DaiLyRepository daiLyRepository;

    public List<DaiLyDTO> getAllDaiLy() {
        return daiLyRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DaiLyDTO getDaiLyById(Integer id) {
        DaiLy daiLy = daiLyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DaiLy not found with id " + id));
        return toDTO(daiLy);
    }

    @Transactional
    public DaiLyDTO createDaiLy(DaiLyDTO dto) {
        DaiLy daiLy = toEntity(dto);
        daiLy.setMaDaiLy(null); // Ensure ID is generated
        return toDTO(daiLyRepository.save(daiLy));
    }

    @Transactional
    public DaiLyDTO updateDaiLy(Integer id, DaiLyDTO dto) {
        DaiLy daiLy = daiLyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DaiLy not found with id " + id));
        
        daiLy.setTenDaiLy(dto.getTenDaiLy());
        daiLy.setDiaChi(dto.getDiaChi());
        daiLy.setSoDienThoai(dto.getSoDienThoai());
        daiLy.setGioLamViec(dto.getGioLamViec());
        daiLy.setMoTa(dto.getMoTa());
        
        return toDTO(daiLyRepository.save(daiLy));
    }

    @Transactional
    public void deleteDaiLy(Integer id) {
        if (!daiLyRepository.existsById(id)) {
            throw new RuntimeException("DaiLy not found with id " + id);
        }
        daiLyRepository.deleteById(id);
    }

    private DaiLyDTO toDTO(DaiLy entity) {
        return DaiLyDTO.builder()
                .maDaiLy(entity.getMaDaiLy())
                .tenDaiLy(entity.getTenDaiLy())
                .diaChi(entity.getDiaChi())
                .soDienThoai(entity.getSoDienThoai())
                .gioLamViec(entity.getGioLamViec())
                .moTa(entity.getMoTa())
                .build();
    }

    private DaiLy toEntity(DaiLyDTO dto) {
        return DaiLy.builder()
                .maDaiLy(dto.getMaDaiLy())
                .tenDaiLy(dto.getTenDaiLy())
                .diaChi(dto.getDiaChi())
                .soDienThoai(dto.getSoDienThoai())
                .gioLamViec(dto.getGioLamViec())
                .moTa(dto.getMoTa())
                .build();
    }
}
