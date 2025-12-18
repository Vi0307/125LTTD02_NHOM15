package com.example.backend.repository;

import com.example.backend.entity.PhuongThucVanChuyenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongThucVanChuyenRepository extends JpaRepository<PhuongThucVanChuyenEntity, Integer> {

}
