package com.example.backend.service;

import com.example.backend.dto.DiaChiDTO;
import com.example.backend.entity.DiaChi;
import com.example.backend.repository.DiaChiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    public List<DiaChiDTO> getDiaChiByUser(Integer maND) {

        List<DiaChi> list = diaChiRepository.findByMaND(maND);

        return list.stream().map(dc -> {
            DiaChiDTO dto = new DiaChiDTO();
            dto.setMaDiaChi(dc.getMaDiaChi());
            dto.setLoaiDiaChi(dc.getLoaiDiaChi());
            dto.setHoTenNguoiNhan(dc.getHoTenNguoiNhan());
            dto.setSoDienThoai(dc.getSoDienThoai());
            dto.setTinhThanhPho(dc.getTinhThanhPho());
            dto.setQuanHuyen(dc.getQuanHuyen());
            dto.setPhuongXa(dc.getPhuongXa());
            dto.setDiaChiChiTiet(dc.getDiaChiChiTiet());
            dto.setMacDinh(dc.getMacDinh());
            return dto;
        }).collect(Collectors.toList());
    }

    public DiaChiDTO getDiaChiMacDinh(Integer maND) {

        DiaChi dc = diaChiRepository.findByMaNDAndMacDinhTrue(maND);

        if (dc == null) return null;

        DiaChiDTO dto = new DiaChiDTO();
        dto.setMaDiaChi(dc.getMaDiaChi());
        dto.setLoaiDiaChi(dc.getLoaiDiaChi());
        dto.setHoTenNguoiNhan(dc.getHoTenNguoiNhan());
        dto.setSoDienThoai(dc.getSoDienThoai());
        dto.setTinhThanhPho(dc.getTinhThanhPho());
        dto.setQuanHuyen(dc.getQuanHuyen());
        dto.setPhuongXa(dc.getPhuongXa());
        dto.setDiaChiChiTiet(dc.getDiaChiChiTiet());
        dto.setMacDinh(dc.getMacDinh());

        return dto;
    }
}
