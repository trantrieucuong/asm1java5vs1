package com.example.demo.controller;

import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChiTietSanPhamController {

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("/chitietsanpham/list")
    public String chiTietSanPhamList(Model model) {
        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAllChiTietSanPham();
        model.addAttribute("chiTietSanPhams", chiTietSanPhams);
        return "chitietsanpham-list";
    }

    @GetMapping("/chitietsanpham/add")
    public String showAddForm(Model model) {
        model.addAttribute("chiTietSanPham", new ChiTietSanPham());
        return "chitietsanpham-add";
    }

    @PostMapping("/chitietsanpham/add")
    public String addChiTietSanPham(ChiTietSanPham chiTietSanPham) {
        chiTietSanPhamService.saveChiTietSanPham(chiTietSanPham);
        return "redirect:/chitietsanpham/list";
    }

    @GetMapping("/chitietsanpham/delete/{id}")
    public String deleteChiTietSanPham(@PathVariable("id") int id) {
        chiTietSanPhamService.deleteChiTietSanPhamById(id);
        return "redirect:/chitietsanpham/list";
    }

    @GetMapping("/chitietsanpham/detail/{id}")
    public String showChiTietSanPhamDetail(@PathVariable("id") int id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getChiTietSanPhamById(id);
        model.addAttribute("chiTietSanPham", chiTietSanPham);
        return "chitietsanpham-detail";
    }

    @GetMapping("/chitietsanpham/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getChiTietSanPhamById(id);
        model.addAttribute("chiTietSanPham", chiTietSanPham);
        return "chitietsanpham-update";
    }

    @PostMapping("/chitietsanpham/update/{id}")
    public String updateChiTietSanPham(@PathVariable("id") int id, ChiTietSanPham updatedChiTietSanPham) {
        ChiTietSanPham existingChiTietSanPham = chiTietSanPhamService.getChiTietSanPhamById(id);
        existingChiTietSanPham.setGiaBan(updatedChiTietSanPham.getGiaBan());
        existingChiTietSanPham.setSoLuongTon(updatedChiTietSanPham.getSoLuongTon());
        existingChiTietSanPham.setTrangThai(updatedChiTietSanPham.getTrangThai());
        chiTietSanPhamService.saveChiTietSanPham(existingChiTietSanPham);
        return "redirect:/chitietsanpham/list";
    }
}
