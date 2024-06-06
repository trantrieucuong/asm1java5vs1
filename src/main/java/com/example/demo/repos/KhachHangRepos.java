package com.example.demo.repos;

import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface KhachHangRepos extends JpaRepository<KhachHang,Integer> {
    @Query("SELECT kh FROM KhachHang kh WHERE kh.sdt = ?1")
    KhachHang findKhachHangBySdt(String sdt);
}