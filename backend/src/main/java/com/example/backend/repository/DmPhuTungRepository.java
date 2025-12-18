package com.example.backend.repository;

import com.example.backend.entity.DmPhuTung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmPhuTungRepository extends JpaRepository<DmPhuTung, String> {
}
