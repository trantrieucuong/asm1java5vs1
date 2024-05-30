package com.example.demo.service;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.repos.ChiTietSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  ChiTietSanPhamService {
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    public ChiTietSanPhamService(ChiTietSanPhamRepository chiTietSanPhamRepository) {
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
    }

    public List<ChiTietSanPham> getAllChiTietSanPham() {
        return chiTietSanPhamRepository.findAll();
    }

    public ChiTietSanPham saveChiTietSanPham(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    public void deleteChiTietSanPhamById(int id) {
        chiTietSanPhamRepository.deleteById(id);
    }

    public ChiTietSanPham getChiTietSanPhamById(int id) {
        return chiTietSanPhamRepository.findById(id).orElse(null);
    }
}
