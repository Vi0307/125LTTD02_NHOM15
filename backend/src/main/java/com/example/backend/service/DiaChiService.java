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

    /**
     * Thêm địa chỉ mới cho người dùng
     */
    public DiaChiDTO addDiaChi(Integer maND, DiaChiDTO diaChiDTO) {
        DiaChi diaChi = new DiaChi();
        diaChi.setMaND(maND);
        diaChi.setLoaiDiaChi(diaChiDTO.getLoaiDiaChi() != null && !diaChiDTO.getLoaiDiaChi().isEmpty()
            ? diaChiDTO.getLoaiDiaChi() : "Nhà riêng");
        diaChi.setHoTenNguoiNhan(diaChiDTO.getHoTenNguoiNhan() != null && !diaChiDTO.getHoTenNguoiNhan().isEmpty()
            ? diaChiDTO.getHoTenNguoiNhan() : "Người nhận");
        diaChi.setSoDienThoai(diaChiDTO.getSoDienThoai() != null && !diaChiDTO.getSoDienThoai().isEmpty()
            ? diaChiDTO.getSoDienThoai() : "0000000000");
        diaChi.setTinhThanhPho(diaChiDTO.getTinhThanhPho() != null && !diaChiDTO.getTinhThanhPho().isEmpty()
            ? diaChiDTO.getTinhThanhPho() : "Chưa cập nhật");
        diaChi.setQuanHuyen(diaChiDTO.getQuanHuyen() != null && !diaChiDTO.getQuanHuyen().isEmpty()
            ? diaChiDTO.getQuanHuyen() : "Chưa cập nhật");
        diaChi.setPhuongXa(diaChiDTO.getPhuongXa() != null && !diaChiDTO.getPhuongXa().isEmpty()
            ? diaChiDTO.getPhuongXa() : "Chưa cập nhật");
        diaChi.setDiaChiChiTiet(diaChiDTO.getDiaChiChiTiet() != null && !diaChiDTO.getDiaChiChiTiet().isEmpty()
            ? diaChiDTO.getDiaChiChiTiet() : "Chưa cập nhật");
        diaChi.setMacDinh(diaChiDTO.getMacDinh() != null ? diaChiDTO.getMacDinh() : false);

        DiaChi saved = diaChiRepository.save(diaChi);

        // Convert to DTO
        DiaChiDTO result = new DiaChiDTO();
        result.setMaDiaChi(saved.getMaDiaChi());
        result.setLoaiDiaChi(saved.getLoaiDiaChi());
        result.setHoTenNguoiNhan(saved.getHoTenNguoiNhan());
        result.setSoDienThoai(saved.getSoDienThoai());
        result.setTinhThanhPho(saved.getTinhThanhPho());
        result.setQuanHuyen(saved.getQuanHuyen());
        result.setPhuongXa(saved.getPhuongXa());
        result.setDiaChiChiTiet(saved.getDiaChiChiTiet());
        result.setMacDinh(saved.getMacDinh());

        return result;
    }
}
