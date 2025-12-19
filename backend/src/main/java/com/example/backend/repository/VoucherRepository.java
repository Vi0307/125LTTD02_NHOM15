package com.example.backend.repository;

import com.example.backend.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

    @Query(value = """
                SELECT v FROM Voucher v
                WHERE v.maND = :maND
                AND v.trangThai = 'Còn hiệu lực'
                AND (v.hanSuDung IS NULL OR v.hanSuDung >= CURRENT_DATE)
            """)
    List<Voucher> getVoucherHopLe(Integer maND);

    // Lấy tất cả voucher của user bất kể trạng thái
    List<Voucher> findByMaND(Integer maND);
}


