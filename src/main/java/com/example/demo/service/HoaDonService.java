package com.example.demo.service;

import com.example.demo.entity.HoaDon;
import com.example.demo.repos.HoaDonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonService {
    private final HoaDonRepository hoaDonRepository;

    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }

    public HoaDon saveHoaDon(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    public void deleteHoaDonById(int id) {
        hoaDonRepository.deleteById(id);
    }

    public HoaDon getHoaDonById(int id) {
        return hoaDonRepository.findById(id).orElse(null);
    }
    public HoaDon getLastSavedHoaDon() {
        return hoaDonRepository.findTopByOrderByIdDesc();
    }
    public List<HoaDon>getHoaDonByTrangThai(String trangThai) {
        return hoaDonRepository.findByTrangThai(trangThai);
    }
    @Transactional
    public void updateOrderStatus(int orderId, String status) {
        HoaDon hoaDon = hoaDonRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        hoaDon.setTrangThai(status);
        hoaDonRepository.save(hoaDon);
    }
}
