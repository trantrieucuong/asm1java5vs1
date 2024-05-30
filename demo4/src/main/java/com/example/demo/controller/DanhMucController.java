package com.example.demo.controller;

import com.example.demo.entity.DanhMuc;
import com.example.demo.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/danhmuc/list")
    public String danhMucList(Model model) {
        List<DanhMuc> danhMucs = danhMucService.getAllDanhMuc();
        model.addAttribute("danhMucs", danhMucs);
        return "danhmuc-list";
    }

    @GetMapping("/danhmuc/add")
    public String showAddForm(Model model) {
        model.addAttribute("danhMuc", new DanhMuc());
        return "danhmuc-add";
    }

    @PostMapping("/danhmuc/add")
    public String addDanhMuc(DanhMuc danhMuc) {
        danhMucService.saveDanhMuc(danhMuc);
        return "redirect:/danhmuc/list";
    }

    @GetMapping("/danhmuc/delete/{id}")
    public String deleteDanhMuc(@PathVariable("id") int id) {
        danhMucService.deleteDanhMucById(id);
        return "redirect:/danhmuc/list";
    }

    @GetMapping("/danhmuc/detail/{id}")
    public String showDanhMucDetail(@PathVariable("id") int id, Model model) {
        DanhMuc danhMuc = danhMucService.getDanhMucById(id);
        model.addAttribute("danhMuc", danhMuc);
        return "danhmuc-detail";
    }

    @GetMapping("/danhmuc/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        DanhMuc danhMuc = danhMucService.getDanhMucById(id);
        model.addAttribute("danhMuc", danhMuc);
        return "danhmuc-update";
    }

    @PostMapping("/danhmuc/update/{id}")
    public String updateDanhMuc(@PathVariable("id") int id, @ModelAttribute("danhMuc") DanhMuc updatedDanhMuc) {
        DanhMuc existingDanhMuc = danhMucService.getDanhMucById(id);
        existingDanhMuc.setMaDanhMuc(updatedDanhMuc.getMaDanhMuc());
        existingDanhMuc.setTenDanhMuc(updatedDanhMuc.getTenDanhMuc());
        existingDanhMuc.setTrangThai(updatedDanhMuc.getTrangThai());
        danhMucService.saveDanhMuc(existingDanhMuc);
        return "redirect:/danhmuc/list";
    }
}
