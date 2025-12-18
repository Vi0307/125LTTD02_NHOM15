package com.example.backend.repository;

import com.example.backend.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {

    List<DiaChi> findByMaND(Integer maND);

    DiaChi findByMaNDAndMacDinhTrue(Integer maND);
}