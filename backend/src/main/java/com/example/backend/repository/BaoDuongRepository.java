package com.example.backend.repository;

import com.example.backend.entity.BaoDuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaoDuongRepository extends JpaRepository<BaoDuong, Integer> {
    List<BaoDuong> findByMaND(Integer maND);
}
