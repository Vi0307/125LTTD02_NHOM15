package com.example.backend.repository;

import com.example.backend.entity.HangXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangXeRepository extends JpaRepository<HangXe, String> {
}
