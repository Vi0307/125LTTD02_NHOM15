package com.example.backend.service;

import com.example.backend.dto.ChiTietLichSuBaoDuongDTO;
import com.example.backend.dto.LichSuBaoDuongDTO;
import com.example.backend.entity.ChiTietLichSuBaoDuong;
import com.example.backend.entity.LichSuBaoDuong;
import com.example.backend.repository.ChiTietLichSuBaoDuongRepository;
import com.example.backend.repository.LichSuBaoDuongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichSuBaoDuongService {

    private final LichSuBaoDuongRepository lichSuBaoDuongRepository;
    private final ChiTietLichSuBaoDuongRepository chiTietLichSuBaoDuongRepository;

    // --- LichSuBaoDuong Operations ---

    public List<LichSuBaoDuongDTO> getAllLichSu() {
        return lichSuBaoDuongRepository.findAll().stream()
                .map(this::toLichSuDTO)
                .collect(Collectors.toList());
    }

    public LichSuBaoDuongDTO getLichSuById(Integer id) {
        LichSuBaoDuong entity = lichSuBaoDuongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History record not found with id " + id));
        return toLichSuDTO(entity);
    }

    public List<LichSuBaoDuongDTO> getLichSuByNguoiDung(Integer maND) {
        return lichSuBaoDuongRepository.findByMaND(maND).stream()
                .map(this::toLichSuDTO)
                .collect(Collectors.toList());
    }


    // --- ChiTietLichSuBaoDuong Operations ---

    public List<ChiTietLichSuBaoDuongDTO> getChiTietByLichSu(Integer maLSBD) {
        return chiTietLichSuBaoDuongRepository.findByMaLSBD(maLSBD).stream()
                .map(this::toChiTietDTO)
                .collect(Collectors.toList());
    }


    // --- Mappers ---

    private LichSuBaoDuongDTO toLichSuDTO(LichSuBaoDuong entity) {
        return LichSuBaoDuongDTO.builder()
                .maLSBD(entity.getMaLSBD())
                .ngayThucHien(entity.getNgayThucHien())
                .maND(entity.getMaND())
                .maXe(entity.getMaXe())
                .maDaiLy(entity.getMaDaiLy())
                .build();
    }

    private LichSuBaoDuong toLichSuEntity(LichSuBaoDuongDTO dto) {
        return LichSuBaoDuong.builder()
                .maLSBD(dto.getMaLSBD())
                .ngayThucHien(dto.getNgayThucHien())
                .maND(dto.getMaND())
                .maXe(dto.getMaXe())
                .maDaiLy(dto.getMaDaiLy())
                .build();
    }

    private ChiTietLichSuBaoDuongDTO toChiTietDTO(ChiTietLichSuBaoDuong entity) {
        return ChiTietLichSuBaoDuongDTO.builder()
                .maCTLSBD(entity.getMaCTLSBD())
                .maLSBD(entity.getMaLSBD())
                .maDaiLy(entity.getMaDaiLy())
                .noiDung(entity.getNoiDung())
                .chiPhi(entity.getChiPhi())
                .build();
    }

    private ChiTietLichSuBaoDuong toChiTietEntity(ChiTietLichSuBaoDuongDTO dto) {
        return ChiTietLichSuBaoDuong.builder()
                .maCTLSBD(dto.getMaCTLSBD())
                .maLSBD(dto.getMaLSBD())
                .maDaiLy(dto.getMaDaiLy())
                .noiDung(dto.getNoiDung())
                .chiPhi(dto.getChiPhi())
                .build();
    }
}
