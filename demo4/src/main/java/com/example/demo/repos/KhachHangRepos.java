package com.example.demo.repos;

import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KhachHangRepos extends JpaRepository<KhachHang,Integer> {
}