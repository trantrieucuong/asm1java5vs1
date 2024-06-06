package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/hoadon/list")
    public String hoaDonList(Model model) {
        List<HoaDon> hoaDons = hoaDonService.getAllHoaDon();
        model.addAttribute("hoaDons", hoaDons);
        return "hoadon-list";
    }

    @GetMapping("/hoadon/add")
    public String showAddForm(Model model) {
        model.addAttribute("hoaDon", new HoaDon());
        return "hoadon-add";
    }

    @PostMapping("/hoadon/add")
    public String addHoaDon(HoaDon hoaDon) {
        hoaDonService.saveHoaDon(hoaDon);
        return "redirect:/hoadon/list";
    }

    @GetMapping("/hoadon/delete/{id}")
    public String deleteHoaDon(@PathVariable("id") int id) {
        hoaDonService.deleteHoaDonById(id);
        return "redirect:/hoadon/list";
    }

    @GetMapping("/hoadon/detail/{id}")
    public String showHoaDonDetail(@PathVariable("id") int id, Model model) {
        HoaDon hoaDon = hoaDonService.getHoaDonById(id);
        model.addAttribute("hoaDon", hoaDon);
        return "hoadon-detail";
    }


    @GetMapping("/hoadon/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        HoaDon hoadon = hoaDonService.getHoaDonById(id);
        model.addAttribute("hoadon", hoadon);
        return "hoadon-update";
    }

    // Xử lý cập nhật hóa đơn
    @PostMapping("/hoadon/update/{id}")
    public String updateHoaDon(@PathVariable("id") int id, @ModelAttribute("hoadon") HoaDon updatedHoaDon) {
        HoaDon existingHoaDon = hoaDonService.getHoaDonById(id);
        existingHoaDon.setDiaChi(updatedHoaDon.getDiaChi());
        existingHoaDon.setSoDienThoai(updatedHoaDon.getSoDienThoai());
        existingHoaDon.setTrangThai(updatedHoaDon.getTrangThai());
        hoaDonService.saveHoaDon(existingHoaDon);
        return "redirect:/hoadon/list"; // Redirect đến trang danh sách hóa đơn sau khi cập nhật thành công
    }
}
