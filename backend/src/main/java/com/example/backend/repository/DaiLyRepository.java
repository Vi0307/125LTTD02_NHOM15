package com.example.backend.repository;

import com.example.backend.entity.DaiLy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaiLyRepository extends JpaRepository<DaiLy, Integer> {
}
