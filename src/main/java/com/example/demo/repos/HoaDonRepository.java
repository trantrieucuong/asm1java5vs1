package com.example.demo.repos;

import com.example.demo.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    HoaDon findTopByOrderByIdDesc();
    List<HoaDon>findByTrangThai(String trangThai);
}
