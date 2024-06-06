package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    @GetMapping("/khachhang/list")
    public String khachhangList(Model model) {
        List<KhachHang>khachHangs=khachHangService.getAllKhachHang();
        model.addAttribute("khachHangs",khachHangs);
        return "khachhang-list";
    }
    @GetMapping("/khachhang/add")
    public String showAddForm(Model model){
        model.addAttribute("KhachHang", new KhachHang());
        return "khachhang-add";
    }

    @PostMapping("/khachhang/add")
    public String addAdmin(KhachHang khachHang){
        khachHangService.saveKhachHang(khachHang);
        return "redirect:/khachhang/list";
    }
    @GetMapping("/khachhang/delete/{id}")
    public String deleteKhachHang(@PathVariable("id") int id) {
        khachHangService.deleteKhachHangById(id);
        return "redirect:/khachhang/list";
    }
    @GetMapping("/khachhang/detail/{id}")
    public String showKhachHangDetail(@PathVariable("id") int id, Model model) {
        KhachHang khachHang = khachHangService.getKhachHangById(id);
        model.addAttribute("khachHang", khachHang);
        return "khachhang-detail";
    }
    @GetMapping("/khachhang/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        KhachHang khachHang = khachHangService.getKhachHangById(id);
        model.addAttribute("khachHang", khachHang);
        return "khachhang-update";
    }

    @PostMapping("/khachhang/update/{id}")
    public String updateKhachHang(@PathVariable("id") int id, KhachHang updatedKhachHang) {
        KhachHang existingKhachHang = khachHangService.getKhachHangById(id);
        existingKhachHang.setHoTen(updatedKhachHang.getHoTen());
        existingKhachHang.setDiaChi(updatedKhachHang.getDiaChi());
        existingKhachHang.setSdt(updatedKhachHang.getSdt());
        existingKhachHang.setTrangThai(updatedKhachHang.getTrangThai());
        khachHangService.saveKhachHang(existingKhachHang);
        return "redirect:/khachhang/list";
    }





}