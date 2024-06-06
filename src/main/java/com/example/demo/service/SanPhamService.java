package com.example.demo.service;

import com.example.demo.entity.SanPham;
import com.example.demo.repos.SanPhamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService {
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public SanPhamService(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }

    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    public SanPham saveSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }

    public void deleteSanPhamById(int id) {
        sanPhamRepository.deleteById(id);
    }

    public SanPham getSanPhamById(int id) {
        return sanPhamRepository.findById(id).orElse(null);
    }
}
