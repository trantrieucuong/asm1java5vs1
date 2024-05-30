package com.example.demo.service;

import com.example.demo.entity.KhachHang;
import com.example.demo.repos.KhachHangRepos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangService {
    private final KhachHangRepos khachHangRepos;

    public KhachHangService(KhachHangRepos khachHangRepos) {
        this.khachHangRepos = khachHangRepos;
    }
    public List<KhachHang>getAllKhachHang() {
        return khachHangRepos.findAll();
    }
    public KhachHang saveKhachHang(KhachHang khachHang) {
        return khachHangRepos.save(khachHang);

    }
    public void deleteKhachHangById(int id) {
        khachHangRepos.deleteById(id);
    }
    public KhachHang getKhachHangById(int id) {
        return khachHangRepos.findById(id).orElse(null);
    }

}