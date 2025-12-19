package com.example.backend.service;

import com.example.backend.dto.VoucherDTO;
import com.example.backend.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository repo;

    public List<VoucherDTO> getVoucherByUser(Integer maND) {
        // Sử dụng findByMaND để lấy danh sách đầy đủ (cả hết hạn/khác trạng thái)
        return repo.findByMaND(maND)
                .stream()
                .map(v -> {
                    VoucherDTO dto = new VoucherDTO();
                    dto.setMaND(v.getMaND());
                    dto.setMaVC(v.getMaVC());
                    dto.setLoaiVoucher(v.getLoaiVoucher());
                    dto.setHanSuDung(v.getHanSuDung());
                    dto.setTrangThai(v.getTrangThai());
                    return dto;
                }).toList();
    }

    public List<VoucherDTO> getAllVouchers() {
        return repo.findAll()
                .stream()
                .map(v -> {
                    VoucherDTO dto = new VoucherDTO();
                    dto.setMaND(v.getMaND());
                    dto.setMaVC(v.getMaVC());
                    dto.setLoaiVoucher(v.getLoaiVoucher());
                    dto.setHanSuDung(v.getHanSuDung());
                    dto.setTrangThai(v.getTrangThai());
                    return dto;
                }).toList();
    }

    public void rewardVoucher(Integer maND, Integer correctAnswers) {
        System.out.println("Processing reward for User: " + maND + ", Score: " + correctAnswers);
        String voucherName = "";
        if (correctAnswers >= 10) {
            voucherName = "Miễn phí Vận chuyển";
        } else if (correctAnswers >= 7) {
            voucherName = "Giảm 50% phí vận chuyển";
        } else {
            System.out.println("Not eligible for reward (Score < 7)");
            return; // Không đủ điều kiện
        }
        System.out.println("Rewarding voucher: " + voucherName);

        com.example.backend.entity.Voucher voucher = new com.example.backend.entity.Voucher();
        voucher.setMaND(maND);
        voucher.setLoaiVoucher(voucherName);
        voucher.setHanSuDung(java.time.LocalDate.now().plusMonths(1)); // Hạn 1 tháng
        voucher.setTrangThai("Còn hiệu lực");
        
        repo.save(voucher);
        System.out.println("Voucher saved successfully");
    }
}

