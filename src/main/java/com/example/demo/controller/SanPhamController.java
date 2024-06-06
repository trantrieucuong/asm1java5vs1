package com.example.demo.controller;

import com.example.demo.entity.SanPham;
import com.example.demo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("/sanpham/list")
    public String sanPhamList(Model model) {
        List<SanPham> sanPhams = sanPhamService.getAllSanPham();
        model.addAttribute("sanPhams", sanPhams);
        return "sanpham-list";
    }

    @GetMapping("/sanpham/add")
    public String showAddForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        return "sanpham-add";
    }

    @PostMapping("/sanpham/add")
    public String addSanPham(SanPham sanPham) {
        sanPhamService.saveSanPham(sanPham);
        return "redirect:/sanpham/list";
    }

    @GetMapping("/sanpham/delete/{id}")
    public String deleteSanPham(@PathVariable("id") int id) {
        sanPhamService.deleteSanPhamById(id);
        return "redirect:/sanpham/list";
    }

    @GetMapping("/sanpham/detail/{id}")
    public String showSanPhamDetail(@PathVariable("id") int id, Model model) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        model.addAttribute("sanPham", sanPham);
        return "sanpham-detail";
    }

    @GetMapping("/sanpham/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        SanPham sanPham = sanPhamService.getSanPhamById(id);
        model.addAttribute("sanPham", sanPham);
        return "sanpham-update";
    }

    @PostMapping("/sanpham/update/{id}")
    public String updateSanPham(@PathVariable("id") int id, SanPham updatedSanPham) {
        SanPham existingSanPham = sanPhamService.getSanPhamById(id);
        existingSanPham.setMaSanPham(updatedSanPham.getMaSanPham());
        existingSanPham.setTenSanPham(updatedSanPham.getTenSanPham());
        existingSanPham.setTrangThai(updatedSanPham.getTrangThai());
        sanPhamService.saveSanPham(existingSanPham);
        return "redirect:/sanpham/list";
    }
}
