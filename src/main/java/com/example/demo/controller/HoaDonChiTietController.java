package com.example.demo.controller;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.service.HoaDonChiTietService;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class HoaDonChiTietController {
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @PostMapping("/hdct/add")
    public String addHoaDonChiTiet(
            @RequestParam("hoaDonId") int hoaDonId,
            @RequestParam("chiTietSanPhamId") int chiTietSanPhamId,
            @RequestParam("soLuong") int soLuong,
            @RequestParam("trangThai") String trangThai) {

        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        hoaDonChiTiet.setHoaDon(hoaDonService.getHoaDonById(hoaDonId));
        hoaDonChiTiet.setChiTietSanPham(chiTietSanPhamService.getChiTietSanPhamById(chiTietSanPhamId));
        hoaDonChiTiet.setSoLuongMua(soLuong);
        hoaDonChiTiet.setGiaBan(hoaDonChiTiet.getChiTietSanPham().getGiaBan());
        hoaDonChiTiet.setTongTien(hoaDonChiTiet.getGiaBan().multiply(BigDecimal.valueOf(soLuong)));
        hoaDonChiTiet.setTrangThai(trangThai);
        hoaDonChiTietService.saveHoaDonChiTiet(hoaDonChiTiet);
        return "redirect:/index?hoaDonId=" + hoaDonId;
    }
}
