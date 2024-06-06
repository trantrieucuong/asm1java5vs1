package com.example.demo.controller;

import com.example.demo.entity.MauSac;
import com.example.demo.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    @GetMapping("/mausac/list")
    public String mauSacList(Model model) {
        List<MauSac> mauSacs = mauSacService.getAllMauSac();
        model.addAttribute("mauSacs", mauSacs);
        return "mausac-list";
    }

    @GetMapping("/mausac/add")
    public String showAddForm(Model model) {
        model.addAttribute("mauSac", new MauSac());
        return "mausac-add";
    }

    @PostMapping("/mausac/add")
    public String addMauSac(MauSac mauSac) {
        mauSacService.saveMauSac(mauSac);
        return "redirect:/mausac/list";
    }

    @GetMapping("/mausac/delete/{id}")
    public String deleteMauSac(@PathVariable("id") int id) {
        mauSacService.deleteMauSacById(id);
        return "redirect:/mausac/list";
    }

    @GetMapping("/mausac/detail/{id}")
    public String showMauSacDetail(@PathVariable("id") int id, Model model) {
        MauSac mauSac = mauSacService.getMauSacById(id);
        model.addAttribute("mauSac", mauSac);
        return "mausac-detail";
    }

    @GetMapping("/mausac/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        MauSac mauSac = mauSacService.getMauSacById(id);
        model.addAttribute("mauSac", mauSac);
        return "mausac-update";
    }

    @PostMapping("/mausac/update/{id}")
    public String updateMauSac(@PathVariable("id") int id, MauSac updatedMauSac) {
        MauSac existingMauSac = mauSacService.getMauSacById(id);
        existingMauSac.setMaMau(updatedMauSac.getMaMau());
        existingMauSac.setTenMau(updatedMauSac.getTenMau());
        existingMauSac.setTrangThai(updatedMauSac.getTrangThai());
        mauSacService.saveMauSac(existingMauSac);
        return "redirect:/mausac/list";
    }
}
