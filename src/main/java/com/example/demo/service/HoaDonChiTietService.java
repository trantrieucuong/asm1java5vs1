package com.example.demo.service;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.repos.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietService {
    private final HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    public HoaDonChiTietService(HoaDonChiTietRepository hoaDonChiTietRepository) {
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
    }

    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
        return hoaDonChiTietRepository.findAll();
    }

    public HoaDonChiTiet saveHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    public void deleteHoaDonChiTietById(int id) {
        hoaDonChiTietRepository.deleteById(id);
    }

    public HoaDonChiTiet getHoaDonChiTietById(int id) {
        return hoaDonChiTietRepository.findById(id).orElse(null);
    }
    public List<HoaDonChiTiet> getHoaDonChiTietsByHoaDonId(int hoaDonId) {
        return hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
    }
}
