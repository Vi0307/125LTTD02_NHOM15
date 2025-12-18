package com.example.backend.service;

import com.example.backend.dto.BaoDuongDTO;
import com.example.backend.dto.ChiTietBaoDuongDTO;
import com.example.backend.entity.BaoDuong;
import com.example.backend.entity.ChiTietBaoDuong;
import com.example.backend.repository.BaoDuongRepository;
import com.example.backend.repository.ChiTietBaoDuongRepository;
// import com.example.backend.repository.XeRepository; // If needed for validation
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaoDuongService {

    private final BaoDuongRepository baoDuongRepository;
    private final ChiTietBaoDuongRepository chiTietBaoDuongRepository;

    // --- BaoDuong Operations ---

    public List<BaoDuongDTO> getAllBaoDuong() {
        return baoDuongRepository.findAll().stream()
                .map(this::toBaoDuongDTO)
                .collect(Collectors.toList());
    }

    public BaoDuongDTO getBaoDuongById(Integer id) {
        BaoDuong entity = baoDuongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found with id " + id));
        return toBaoDuongDTO(entity);
    }

    public List<BaoDuongDTO> getBaoDuongByNguoiDung(Integer maND) {
        return baoDuongRepository.findByMaND(maND).stream()
                .map(this::toBaoDuongDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BaoDuongDTO createBaoDuong(BaoDuongDTO dto) {
        BaoDuong entity = toBaoDuongEntity(dto);
        entity.setMaBD(null);
        if (entity.getTrangThai() == null) entity.setTrangThai("Nhắc nhở");
        if (entity.getDaNhacNho() == null) entity.setDaNhacNho(false);
        
        return toBaoDuongDTO(baoDuongRepository.save(entity));
    }



    // --- ChiTietBaoDuong Operations ---

    public List<ChiTietBaoDuongDTO> getChiTietByBaoDuong(Integer maBD) {
        return chiTietBaoDuongRepository.findByMaBD(maBD).stream()
                .map(this::toChiTietDTO)
                .collect(Collectors.toList());
    }


    // --- Mappers ---

    private BaoDuongDTO toBaoDuongDTO(BaoDuong entity) {
        return BaoDuongDTO.builder()
                .maBD(entity.getMaBD())
                .ngayBaoDuong(entity.getNgayBaoDuong())
                .moTa(entity.getMoTa())
                .maVC(entity.getMaVC())
                .trangThai(entity.getTrangThai())
                .daNhacNho(entity.getDaNhacNho())
                .maND(entity.getMaND())
                .build();
    }

    private BaoDuong toBaoDuongEntity(BaoDuongDTO dto) {
        return BaoDuong.builder()
                .maBD(dto.getMaBD())
                .ngayBaoDuong(dto.getNgayBaoDuong())
                .moTa(dto.getMoTa())
                .maVC(dto.getMaVC())
                .trangThai(dto.getTrangThai())
                .daNhacNho(dto.getDaNhacNho())
                .maND(dto.getMaND())
                .build();
    }

    private ChiTietBaoDuongDTO toChiTietDTO(ChiTietBaoDuong entity) {
        return ChiTietBaoDuongDTO.builder()
                .maCTBD(entity.getMaCTBD())
                .maBD(entity.getMaBD())
                .maXe(entity.getMaXe())
                .moTa(entity.getMoTa())
                .soLanBaoDuong(entity.getSoLanBaoDuong())
                .build();
    }

    private ChiTietBaoDuong toChiTietEntity(ChiTietBaoDuongDTO dto) {
        return ChiTietBaoDuong.builder()
                .maCTBD(dto.getMaCTBD())
                .maBD(dto.getMaBD())
                .maXe(dto.getMaXe())
                .moTa(dto.getMoTa())
                .soLanBaoDuong(dto.getSoLanBaoDuong())
                .build();
    }
}
