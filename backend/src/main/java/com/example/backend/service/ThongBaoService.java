package com.example.backend.service;

import com.example.backend.dto.ThongBaoDTO;
import com.example.backend.entity.ThongBao;
import com.example.backend.repository.ThongBaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThongBaoService {

    private final ThongBaoRepository thongBaoRepository;

    public List<ThongBaoDTO> getThongBaoByMaND(Integer maND) {
        return thongBaoRepository.findByMaND(maND).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ThongBaoDTO toDTO(ThongBao entity) {
        return ThongBaoDTO.builder()
                .maThongBao(entity.getMaThongBao())
                .maND(entity.getMaND())
                .tieuDe(entity.getTieuDe())
                .noiDung(entity.getNoiDung())
                .ngayTao(entity.getNgayTao())
                .build();
    }

    private ThongBao toEntity(ThongBaoDTO dto) {
        return ThongBao.builder()
                .maThongBao(dto.getMaThongBao())
                .maND(dto.getMaND())
                .tieuDe(dto.getTieuDe())
                .noiDung(dto.getNoiDung())
                .ngayTao(dto.getNgayTao())
                .build();
    }
}
