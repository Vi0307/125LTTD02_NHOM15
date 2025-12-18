package com.example.backend.repository;

import com.example.backend.entity.Xe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XeRepository extends JpaRepository<Xe, String> {
    List<Xe> findByMaND(Integer maND);
}
